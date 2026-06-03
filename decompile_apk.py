#!/usr/bin/env python
"""APK decompilation script using androguard."""
import logging, sys, os

# Suppress all logging
logging.disable(logging.CRITICAL)

# Redirect stderr to null
sys.stderr = open(os.devnull, 'w')

from androguard.misc import AnalyzeAPK
from androguard.decompiler.decompiler import DecompilerDAD

OUT_DIR = "decompiled_output"
os.makedirs(OUT_DIR, exist_ok=True)

print("[*] Loading APK...")
a, d, dx = AnalyzeAPK('base.apk')

# 1. Extract AndroidManifest.xml
print("[*] Extracting AndroidManifest.xml...")
try:
    from androguard.core.bytecode import AXMLPrinter
    axml = a.get_android_manifest_axml()
    buff = axml.get_buff()
    ap = AXMLPrinter(buff)
    with open(os.path.join(OUT_DIR, "AndroidManifest.xml"), "w", encoding="utf-8") as f:
        f.write(ap.get_xml())
    print("  -> Saved AndroidManifest.xml")
except Exception as e:
    print(f"  -> Error extracting manifest: {e}")

# 2. Extract strings from DEX
print("[*] Extracting all strings from DEX...")
all_strings = set()
for cls in dx.get_classes():
    for m in cls.get_methods():
        try:
            for s in m.get_strings():
                if isinstance(s, str) and len(s) > 3:
                    all_strings.add(s)
        except:
            pass

with open(os.path.join(OUT_DIR, "all_strings.txt"), "w", encoding="utf-8") as f:
    for s in sorted(all_strings):
        f.write(s + "\n")
print(f"  -> Saved {len(all_strings)} strings")

# 3. Extract app-specific classes and decompile them
APP_PACKAGE = "org/aquamarine5/brainspark/chaoxingsignfaker"
print(f"[*] Extracting {APP_PACKAGE} classes...")

app_classes = []
for cls in dx.get_classes():
    if APP_PACKAGE in cls.name:
        app_classes.append(cls)

print(f"  -> Found {len(app_classes)} app-specific classes")

app_dir = os.path.join(OUT_DIR, "app_sources")
os.makedirs(app_dir, exist_ok=True)

for cls in app_classes:
    class_name = cls.name.replace('/', '.').lstrip('L').rstrip(';')
    class_file = class_name + ".java"
    filepath = os.path.join(app_dir, class_file.replace('.', '/'))
    os.makedirs(os.path.dirname(filepath), exist_ok=True)

    try:
        decompiler = DecompilerDAD(d, dx)
        source = decompiler.get_source_class(cls)
        with open(filepath, "w", encoding="utf-8") as f:
            f.write(source)
    except Exception as e:
        # Fallback: write smali-style info
        with open(filepath, "w", encoding="utf-8") as f:
            f.write(f"// Decompilation failed: {e}\n")
            f.write(f"// Class: {cls.name}\n")
            for m in cls.get_methods():
                f.write(f"// Method: {m.name} {m.get_descriptor()}\n")

print(f"  -> Decompiled classes saved to {app_dir}/")

# 4. Extract all class names for reference
print("[*] Saving all class names...")
classes = sorted(dx.get_classes(), key=lambda c: c.name)
with open(os.path.join(OUT_DIR, "all_classes.txt"), "w", encoding="utf-8") as f:
    for cls in classes:
        f.write(cls.name + "\n")
print(f"  -> Saved {len(classes)} class names")

print(f"\n[*] Done! Output in '{OUT_DIR}/' directory.")
