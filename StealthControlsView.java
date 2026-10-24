
package com.scpt.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class StealthControlsView extends View {

    public interface KeySink {
        void keyDown(int key);
        void keyUp(int key);
    }

    private final KeySink sink;
    private final Paint paint = new Paint(3);

    private int stickPointer = -1;
    private int stickKey = -1;

    private final int[] pointerIds = {-1, -1, -1, -1};
    private final int[] buttonKeys = {5, 2, 8, 0};
    private final String[] labels = {"A", "B", "C", "D"};

    private final int[] allKeys = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 0
    };

    private final int[] keyHolds = new int[10];

    private float stickX, stickY, stickRadius;
    private float buttonRadius;

    public StealthControlsView(Context context, KeySink sink) {
        super(context);
        this.sink = sink;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void sendKey(int key, boolean pressed) {
        for (int i = 0; i < allKeys.length; i++) {
            if (allKeys[i] != key) continue;

            if (pressed) {
                if (keyHolds[i]++ == 0) sink.keyDown(key);
            } else if (keyHolds[i] > 0) {
                if (--keyHolds[i] == 0) sink.keyUp(key);
            }
            break;
        }
    }

    private int direction(float x, float y) {
        double dx = x - stickX;
        double dy = y - stickY;

        if (Math.hypot(dx, dy) < stickRadius * 0.23) {
            return -1;
        }

        int sector = ((int) Math.round(
            Math.atan2(dy, dx) / (Math.PI / 4)
        ) + 8) % 8;

        int[] directions = {6, 9, 8, 7, 4, 1, 2, 3};
        return directions[sector];
    }

    private void updateStick(int next) {
        if (next == stickKey) return;

        if (stickKey != -1) sendKey(stickKey, false);
        stickKey = next;
        if (next != -1) sendKey(next, true);

        invalidate();
    }

    private float bx(int index) {
        return getWidth() * (index % 2 == 0 ? 0.72f : 0.88f);
    }

    private float by(int index) {
        return getHeight() * (index < 2 ? 0.35f : 0.70f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = getWidth();
        float h = getHeight();

        stickX = w * 0.23f;
        stickY = h * 0.53f;
        stickRadius = Math.min(w * 0.18f, h * 0.38f);
        buttonRadius = Math.min(w * 0.065f, h * 0.16f);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xD0203030);
        canvas.drawCircle(
            stickX, stickY, stickRadius, paint
        );

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(0xFF83D9A0);
        canvas.drawCircle(
            stickX, stickY, stickRadius, paint
        );

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFF395B4A);
        canvas.drawCircle(
            stickX, stickY, stickRadius * 0.48f, paint
        );

        for (int i = 0; i < 4; i++) {
            paint.setColor(
                i == 0 ? 0xFF266B46 : 0xFF304440
            );

            canvas.drawCircle(
                bx(i), by(i), buttonRadius, paint
            );

            paint.setColor(0xFFB9FFD0);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(buttonRadius * 0.85f);

            canvas.drawText(
                labels[i],
                bx(i),
                by(i) + buttonRadius * 0.29f,
                paint
            );
        }
    }

    private void release(int id) {
        if (id == stickPointer) {
            stickPointer = -1;
            updateStick(-1);
        }

        for (int i = 0; i < 4; i++) {
            if (pointerIds[i] == id) {
                sendKey(buttonKeys[i], false);
                pointerIds[i] = -1;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int id = event.getPointerId(index);

        if (action == MotionEvent.ACTION_DOWN ||
            action == MotionEvent.ACTION_POINTER_DOWN) {

            float x = event.getX(index);
            float y = event.getY(index);

            if (x < getWidth() * 0.48f &&
                stickPointer == -1) {

                stickPointer = id;
                updateStick(direction(x, y));
                return true;
            }

            for (int i = 0; i < 4; i++) {
                if (Math.hypot(
                    x - bx(i), y - by(i)
                ) < buttonRadius * 1.25f &&
                    pointerIds[i] == -1) {

                    pointerIds[i] = id;
                    sendKey(buttonKeys[i], true);
                    invalidate();
                    return true;
                }
            }

            return true;
        }

        if (action == MotionEvent.ACTION_MOVE) {
            if (stickPointer != -1) {
                int position =
                    event.findPointerIndex(stickPointer);

                if (position >= 0) {
                    updateStick(direction(
                        event.getX(position),
                        event.getY(position)
                    ));
                }
            }
            return true;
        }

        if (action == MotionEvent.ACTION_UP ||
            action == MotionEvent.ACTION_POINTER_UP) {

            release(id);
            invalidate();
            return true;
        }

        if (action == MotionEvent.ACTION_CANCEL) {
            stickPointer = -1;
            stickKey = -1;

            for (int i = 0; i < 4; i++) {
                pointerIds[i] = -1;
            }

            for (int i = 0; i < allKeys.length; i++) {
                if (keyHolds[i] > 0) {
                    keyHolds[i] = 0;
                    sink.keyUp(allKeys[i]);
                }
            }

            invalidate();
            return true;
        }

        return true;
    }
}
