# Gadget
自定义组件库

## v1.0.0
#### ShadowPopupWindow

- 仿UC底部弹出菜单。<br>
![image](https://github.com/Z-bm/Gadget/blob/master/img/shadow.gif)

###### 使用
在project下build.gradle内添加

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

在module下build.gradle内添加

	dependencies {
	        implementation 'com.github.Z-bm:Gadget:v1.0.0'
	}
  
