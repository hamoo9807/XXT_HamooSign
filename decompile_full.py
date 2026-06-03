#!/usr/bin/env python
"""Full APK decompilation using androguard - outputs everything to files."""
import os, sys

# Suppress loguru output
import loguru
loguru.logger.remove()

from androguard.misc import AnalyzeAPK
from androguard.decompiler.decompiler import DecompilerDAD

OUT_DIR = "decompiled_full"
os.makedirs(OUT_DIR, exist_ok=True)

# Load APK (multi-dex supported)
result = AnalyzeAPK('base.apk')
a = result[0]
dex_list = result[1] if isinstance(result[1], list) else [result[1]]
dx = result[2]

print(f"[*] Loaded {len(dex_list)} DEX files")

# 1. Extract AndroidManifest.xml
print("[*] Extracting AndroidManifest.xml ...")
try:
    from androguard.core.bytecode import get_xml_from_axml
    buff = a.get_android_manifest_axml().get_buff()
    xml_str = get_xml_from_axml(buff)
    with open(os.path.join(OUT_DIR, "AndroidManifest.xml"), "w", encoding="utf-8") as f:
        f.write(xml_str)
    print("  -> OK")
except Exception as e:
    print(f"  -> Error: {e}")

# 2. App info
print("[*] Writing app info ...")
with open(os.path.join(OUT_DIR, "app_info.txt"), "w", encoding="utf-8") as f:
    f.write(f"Package: {a.get_package()}\n")
    f.write(f"Version: {a.get_androidversion_name()}\n")
    f.write(f"Version Code: {a.get_androidversion_code()}\n")
    f.write(f"Min SDK: {a.get_min_sdk_version()}\n")
    f.write(f"Target SDK: {a.get_target_sdk_version()}\n\n")
    f.write("Permissions:\n")
    for p in a.get_permissions():
        f.write(f"  {p}\n")
    f.write("\nActivities:\n")
    for act in a.get_activities():
        f.write(f"  {act}\n")
    f.write("\nServices:\n")
    for s in a.get_services():
        f.write(f"  {s}\n")
    f.write("\nReceivers:\n")
    for r in a.get_receivers():
        f.write(f"  {r}\n")
    f.write("\nProviders:\n")
    for p in a.get_providers():
        f.write(f"  {p}\n")
print("  -> OK")

# 3. All class names (from all DEX files)
print("[*] Saving all class names ...")
all_class_names = set()
for dex in dex_list:
    for cls in dex.get_classes():
        all_class_names.add(cls.name)
with open(os.path.join(OUT_DIR, "all_classes.txt"), "w", encoding="utf-8") as f:
    for name in sorted(all_class_names):
        f.write(name + "\n")
print(f"  -> {len(all_class_names)} classes")

# 4. App-specific classes with Java source
APP_PKG = "org/aquamarine5/brainspark/chaoxingsignfaker"
app_out = os.path.join(OUT_DIR, "app_sources")
os.makedirs(app_out, exist_ok=True)

print(f"[*] Decompiling {APP_PKG} classes ...")
dad = DecompilerDAD(dex_list[0], dx)
count = 0

for dex in dex_list:
    for cls_def in dex.get_classes():
        if APP_PKG not in cls_def.name:
            continue

        path = cls_def.name.lstrip('L').rstrip(';') + ".java"
        filepath = os.path.join(app_out, path)
        os.makedirs(os.path.dirname(filepath), exist_ok=True)

        try:
            source = dad.get_source_class(cls_def)
            if source:
                with open(filepath, "w", encoding="utf-8") as f:
                    f.write(source)
            else:
                with open(filepath, "w", encoding="utf-8") as f:
                    f.write(f"// Empty source for: {cls_def.name}\n")
        except Exception as e:
            with open(filepath, "w", encoding="utf-8") as f:
                f.write(f"// Decompilation error for {cls_def.name}: {e}\n")
                for m in cls_def.get_methods():
                    f.write(f"// Method: {m.name} {m.get_descriptor()}\n")
        count += 1
        if count % 20 == 0:
            print(f"  -> {count} classes decompiled...")

print(f"  -> Done! {count} classes decompiled")
print(f"\n[*] All output in '{OUT_DIR}/' directory")
