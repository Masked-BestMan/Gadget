# Gadget
自定义组件库

## v1.0.0
#### ShadowPopupWindow

- 仿UC底部弹出菜单。<br>
![image](https://github.com/Z-bm/Gadget/blob/master/img/popup.gif)

###### 使用
```
ShadowPopupWindow shadowPopupWindow=new ShadowPopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, 600);
shadowPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
shadowPopupWindow.setOutsideTouchable(true);
shadowPopupWindow.setFocusable(true);
shadowPopupWindow.shadowAbove(view);           //指定阴影背景在某个view上显示，需要在显示PopupWindow前调用才有效果
shadowPopupWindow.showAsDropDown(view,0,0);    //使用该方法显示PopupWindow需要明确指定窗口的大小，不能使用WRAP_CONTENT
```

###### 引用
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
  
