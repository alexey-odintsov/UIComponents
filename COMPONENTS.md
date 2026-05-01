# UI Components

This document describes the UI components available in this library and their platform support.



## Platform Support Comparison

| Component | Common | Desktop (JVM) | Android | Description |
| :--- | :---: | :---: | :---: | :--- |
| **Common Components** | | | | |
| [StatusBar](library/src/commonMain/kotlin/alexey/odintsov/uicomponents/StatusBar.kt) | ✅ | ✅ | ✅ | Status text and progress indicator |
| [Table](library/src/commonMain/kotlin/alexey/odintsov/uicomponents/table/Table.kt) | ✅ | ✅ | ✅ | Flexible table with selection and resizing |
| [Table2](library/src/commonMain/kotlin/alexey/odintsov/uicomponents/table/Table2.kt) | ✅ | ✅ | ✅ | Alternative table implementation |
| [ColumnResizerDivider](library/src/commonMain/kotlin/alexey/odintsov/uicomponents/table/ColumnResizerDivider.kt) | ✅ | ✅ | ✅ | Divider for manual column resizing |
| [PointerIconProvider](library/src/commonMain/kotlin/alexey/odintsov/uicomponents/PointerIconProvider.kt) | ✅ | ✅ | ✅ | Platform-specific mouse/pointer icons |
| **Buttons** | | | | |
| [CustomButton](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/buttons/CustomButton.kt) | — | ✅ | — | Customizable button with specific shapes/colors |
| [ImageButton](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/buttons/ImageButton.kt) | — | ✅ | — | Button displaying an icon/image |
| [ToggleImageButton](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/buttons/ToggleImageButton.kt) | — | ✅ | — | Image button with on/off state |
| [CustomDropDownButton](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/buttons/CustomDropDownButton.kt) | — | ✅ | — | Button with dropdown menu |
| **Input Fields** | | | | |
| [CustomEditText](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/edit/CustomEditText.kt) | — | ✅ | — | Customizable text input |
| [AutoCompleteEditText](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/edit/AutoCompleteEditText.kt) | — | ✅ | — | Text field with suggestions |
| [CustomCheckbox](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/checkbox/CustomCheckbox.kt) | — | ✅ | — | Boolean input checkbox |
| **Dialogs & Windows** | | | | |
| [FileDialog](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/dialogs/FileDialog.kt) | — | ✅ | — | Native file picker dialog |
| [ColorPickerDialog](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/dialogs/ColorPickerDialog.kt) | — | ✅ | — | Visual color selection dialog |
| [DesktopDialogWindow](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/dialogs/DesktopDialogWindow.kt) | — | ✅ | — | Base for custom desktop windows |
| **Layout & Navigation** | | | | |
| [TabsPanel](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/tabs/TabsPanel.kt) | — | ✅ | — | Tabbed interface management |
| [CustomDropDown](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/CustomDropDown.kt) | — | ✅ | — | Customizable dropdown menu |
| **Other** | | | | |
| [DesktopTable](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/table/DesktopTable.kt) | — | ✅ | — | Desktop-optimized table |
| [Tooltip](library/src/jvmMain/kotlin/alexey/odintsov/uicomponents/Tooltip.kt) | — | ✅ | — | Hover information popup |
