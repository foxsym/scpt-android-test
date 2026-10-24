#!/usr/bin/env python3
"""Prepare the J2ME Loader midlet variant using a legally supplied game JAR."""
from pathlib import Path
import zipfile,sys,shutil,xml.etree.ElementTree as ET

if len(sys.argv)!=3:
    raise SystemExit('Usage: python prepare_port.py path/to/J2ME-Loader path/to/SC-PT_E1000_EN_102.jar')
repo=Path(sys.argv[1]).resolve()
jar=Path(sys.argv[2]).resolve()
if not (repo/'app'/'build.gradle').exists():
    raise SystemExit('Not a J2ME-Loader source checkout: missing app/build.gradle')
if not jar.is_file():
    raise SystemExit('JAR not found: '+str(jar))
out=repo/'app'/'src'/'midlet'/'resources'
out.mkdir(parents=True,exist_ok=True)
with zipfile.ZipFile(jar) as z:
    mf=z.read('META-INF/MANIFEST.MF')
    if b'MIDlet-1:' not in mf or b'SC-PT' not in mf:
        raise SystemExit('This does not look like the expected SC-PT MIDlet')
    for i in z.infolist():
        p=Path(i.filename)
        if p.is_absolute() or '..' in p.parts:raise SystemExit('Unsafe entry')
        if i.is_dir() or str(p).startswith('META-INF/'):
            continue
        target=out/p
        target.parent.mkdir(parents=True,exist_ok=True)
        with z.open(i) as source,open(target,'wb') as destination:
            shutil.copyfileobj(source,destination)
m=(out/'MIDLET-META-INF'/'MANIFEST.MF')
m.parent.mkdir(exist_ok=True,parents=True)
m.write_bytes(mf)
# Force the MIDlet launcher to portrait, preserving the upstream manifest configuration.
manifest=repo/'app'/'src'/'midlet'/'AndroidManifest.xml'
if manifest.is_file():
    android='http://schemas.android.com/apk/res/android'
    ET.register_namespace('android',android)
    ET.register_namespace('tools','http://schemas.android.com/tools')
    tree=ET.parse(manifest)
    for a in tree.getroot().iter('activity'):
        if a.get('{'+android+'}name')=='javax.microedition.shell.MicroActivity':
            a.set('{'+android+'}screenOrientation','portrait')
    tree.write(manifest,encoding='utf-8',xml_declaration=True)
else:
    print('Warning: cannot locate midlet AndroidManifest; set MicroActivity portrait manually.')
print('Prepared SC-PT class files/resources, MIDlet manifest, and portrait launcher.')
print('Build with: ./gradlew :app:assembleMidletDebug')
print('NOTE: Stylized buttons require further implementation in the emulator source.')
