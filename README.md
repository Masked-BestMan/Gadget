# Gadget
自定义组件库

#### ShadowPopupLayout——仿UC弹出菜单
## v1.1.0
1. 更换弹出窗布局
2. 增加自定义属性

## v1.0.0
1. 正式版

效果
-
![image](https://github.com/Z-bm/Gadget/blob/master/img/popup.gif)

属性
-
* navigationBarLayout：导航栏布局
* popupWindowLayout：弹出窗布局
* orientation：导航栏位置，top/bottom

方法
-
* setOnDismissListener(OnDismissListener onDismissListener)：弹出窗关闭监听
* getBarView()：导航栏视图
* getWindowView()：弹出窗视图
* isShowing()：是否弹出
* showPopupView()：弹出窗口
* disMissPopupView()：关闭窗口

使用
-
```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".TestActivity">
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <!--主内容区，最好在ShadowPopupLayout前定义；
            不能使用layout_below或layout_above，
            否则弹出窗会将该区域顶出-->
    </FrameLayout>
    <com.zbm.shadowpopuplayout.ShadowPopupLayout
        android:id="@+id/window_view"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        app:navigationBarLayout="@layout/bottom_bar"
        app:popupWindowLayout="@layout/dark_popup_window"
        app:orientation="top" />
</RelativeLayout>
```
```
ShadowPopupLayout windowView=findViewById(R.id.window_view);
Button button=windowView.getBarView().findViewById(R.id.menu);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        windowView.showPopupView();
    }
});
```

引用
-
在project下build.gradle内添加
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

在module下build.gradle内添加
```
dependencies {
	implementation 'com.github.Z-bm:Gadget:v1.0.0'
}
```
