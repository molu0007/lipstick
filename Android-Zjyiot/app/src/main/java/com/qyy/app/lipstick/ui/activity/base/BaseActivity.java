package com.qyy.app.lipstick.ui.activity.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ibupush.molu.common.net.CallDelegate;
import com.ibupush.molu.common.util.LogUtil;
import com.ibupush.molu.common.util.TextUtil;
import com.ibupush.molu.common.util.ToastUtils;
import com.qyy.app.lipstick.BuildConfig;
import com.qyy.app.lipstick.R;
import com.qyy.app.lipstick.event.EventManager;
import com.qyy.app.lipstick.event.EventType;
import com.qyy.app.lipstick.event.MessageEvent;
import com.qyy.app.lipstick.utils.StatusBarUtil;
import com.qyy.app.lipstick.views.UploadingView;
import com.tbruyelle.rxpermissions2.RxPermissions;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * Activity的基类
 * Created by 曾丽 on 2017/6/26.
 *
 * @author hoop
 */
public abstract class BaseActivity extends AppCompatActivity implements CallDelegate {
    protected final String TAG = getClass().getSimpleName();
    private Toolbar toolbar;
    /**
     * 导航图标资源id(一般为返回键)
     */
    private int toolbarNavigationIconResId;
    private Drawable toolbarNavigationIconDrawable;
    /**
     * Toolbar中居中展示的TextView，用于显示页面标题
     */
    protected TextView tvTitle;
    /**
     * 加载中进度圈
     */
    private UploadingView mLoadingView;
    private RxPermissions rxPermissions;
    private Unbinder mBind;

    /**
     * 授权结果
     */
    protected boolean mIsAccredited;


    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar(this);
        if (!hasWindowBackGround()) {
            getWindow().setBackgroundDrawable(null);
        }
        LogUtil.v(TAGS.UI_LIFECYCLE, "onCreate=" + TAG);
        final int layoutResId = getContentViewId();
        if (layoutResId > 0) {
            setContentView(layoutResId);
        }
        mBind = ButterKnife.bind(this);
        EventManager.register(this);
        initToolBar();
        initView(savedInstanceState);
        initData(savedInstanceState);
        initListener();
        final String[] grantPermissions = getGrantPermissions();
        if (grantPermissions != null && grantPermissions.length > 0) {
            requestPermissions(grantPermissions);
        }
//        ExitApplication.getInstance().addActivity(this);
    }

    /**
     * 获取要授予的权限数组，如果指定了权限则会在Activity的{@link #onCreate(Bundle)}方法中申请运行时权限
     * <p>返回示例 return new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission
     * .CAMERA};</p>
     *
     * @return 要申请授予的权限数组，返回null或数组为空，则不会在{@link #onCreate(Bundle)}中申请权限
     */
    protected String[] getGrantPermissions() {
        return null;
    }

    /**
     * 用户拒绝授权时候的回调
     */
    protected void onPermissionsDenied() {
        mIsAccredited = false;
    }

    /**
     * 授权成功时候的回调
     */
    protected void onPermissionsGranted() {
        mIsAccredited = true;
    }

    /**
     * 用户拒绝授权时候的回调
     *
     * @param perCode 申请权限的请求码
     */
    protected void onPermissionsDenied(int perCode) {
        onPermissionsDenied();
    }

    /**
     * 授权成功时候的回调
     *
     * @param perCode 申请权限的请求码
     */
    protected void onPermissionsGranted(int perCode) {
        onPermissionsGranted();
    }

    /**
     * 任务栏透明
     *
     * @param context 上下文
     */
    public void setStatusBar(Activity context) {
        //不需要统一处理,直接返回
        if (!initStatusBar()) {
            return;
        }
        transparentStatusbar(context);
        if (isLightMode()) {
            StatusBarUtil.statusBarLightMode(this);
        }
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
//        StatService.onResume(this);
        if (BuildConfig.DEBUG) {
            LogUtil.v(TAGS.UI_LIFECYCLE, "onResume=" + TAG);
        }
    }

    @CallSuper
    @Override
    protected void onPause() {
        super.onPause();
//        StatService.onPause(this);
        if (BuildConfig.DEBUG) {
            LogUtil.v(TAGS.UI_LIFECYCLE, "onPause=" + TAG);
        }
    }

    @CallSuper
    @Override
    protected void onRestart() {
        super.onRestart();
        if (BuildConfig.DEBUG) {
            LogUtil.v(TAG, "onRestart");
        }
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.v(TAGS.UI_LIFECYCLE, "onDestroy=" + TAG);
        mBind.unbind();
//        ExitApplication.getInstance().removeActivity(this);
        EventManager.unregister(this);
    }

    @Override
    public void onBackPressed() {
        // 获取activeFragment:即从栈顶开始 状态为show的那个Fragment
        BaseFragment activeFragment = getActiveFragment(getSupportFragmentManager(), null);
        if (dispatchBackPressedEvent(activeFragment)) {
            return;
        }
        onBackPressedSupport();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (BuildConfig.DEBUG) {
            LogUtil.v(TAG, "onCreateOptionsMenu");
        }
        final int menuResId = getToolbarMenuResId();
        if (menuResId > 0) {
            getMenuInflater().inflate(menuResId, menu);
            onToolbarMenuCreated(menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        onToolbarMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    @CallSuper
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.v(TAG, "onActivityResult -> requestCode = " +
                requestCode + ",resultCode = " + resultCode + ",Intent data = " + data);
        // 解决Activity中包含Fragment时无法回调Fragment中的同名方法
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            @SuppressLint("RestrictedApi") final List<Fragment> fragments = fragmentManager
                    .getFragments();
            if (fragments != null && !fragments.isEmpty()) {
                for (Fragment fragment : fragments) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    @MenuRes
    protected int getToolbarMenuResId() {
        return 0;
    }

    protected void onToolbarMenuCreated(Menu menu) {

    }

    protected void onToolbarNavigationClick() {
        onBackPressedSupport();
    }

    protected void onToolbarMenuItemClick(MenuItem menuItem) {

    }

    /**
     * 初始化控件,选择性复写
     *
     * @param savedInstanceState saved bundle of Activity
     */
    protected void initView(Bundle savedInstanceState) {

    }

    /**
     * 初始化页面数据(选择性复写)
     *
     * @param savedInstanceState saved bundle of Activity
     */
    protected void initData(Bundle savedInstanceState) {

    }

    /**
     * 初始化监听(选择性复写,当ButterKnife不能满足需求时使用)
     */
    protected void initListener() {

    }

    /**
     * 是否需要window的背景绘制
     *
     * @return true-->有window背景;false-->没有window背景
     */
    protected boolean hasWindowBackGround() {
        return true;
    }

    /**
     * 申请运行时权限，处理Android 6.0及其以上版本权限
     * <p>示例：requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA)
     * 或requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest
     * .permission.CAMERA})</p>
     *
     * @param permissions 要申请的权限数组
     */
    public final void requestPermissions(@NonNull String... permissions) {
        requestPermissions(-1, permissions);
    }

    /**
     * 申请运行时权限，处理Android 6.0及其以上版本权限
     * <p>示例：requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA)
     * 或requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest
     * .permission.CAMERA})</p>
     *
     * @param permissions 要申请的权限数组
     * @param perCode     权限申请的请求码
     */
    public final void requestPermissions(final int perCode, @NonNull String... permissions) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(this);
        }
        rxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            onPermissionsGranted(perCode);
                        } else {
                            onPermissionsDenied(perCode);
                        }
                    }
                });
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onToolbarNavigationClick();
                }
            });

            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    onToolbarMenuItemClick(item);
                    return false;
                }
            });
        } else {
            LogUtil.w("May be you are not define an id named 'toolbar' in your layout.");
        }
        // 隐藏Toolbar title
        hideToolbarTitle();
        setToolbarNavigationIcon(R.drawable.fh);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCenterTitleClick();
                }
            });
        }
    }

    /**
     * When the title clicked this method will be invoked.
     */
    protected void onCenterTitleClick() {

    }

    /**
     * Set the center title of this toolbar.
     * <p>
     * <p>A title should be used as the anchor for a section of content. It should
     * describe or name the content being viewed.</p>
     *
     * @param resId Resource ID of a string to set as the title
     */
    public void setCenterTitleText(@StringRes int resId) {
        setCenterTitleText(getText(resId));
    }

    /**
     * Set the center title of this toolbar.
     * <p>
     * <p>A title should be used as the anchor for a section of content. It should
     * describe or name the content being viewed.</p>
     *
     * @param title Title to set
     */
    public void setCenterTitleText(CharSequence title) {
        if (tvTitle == null) {
            throw new NullPointerException("你可能没有在布局文件中定义一个ID为R.id.tv_title的TextView.");
        }
        tvTitle.setText(title);
    }

    public void setCenterTitleTextColor(@ColorInt int color) {
        tvTitle.setTextColor(color);
    }

    public final Toolbar getToolbar() {
        return toolbar;
    }

    public final void hideToolbarTitle() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * Hide the icon to use for the toolbar's navigation button.
     */
    public void hideToolbarNavigationIcon() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(null);
        }
    }

    /**
     * Show the icon to use for the toolbar's navigation button.
     */
    public void showToolbarNavigationIcon() {
        if (toolbar != null) {
            if (toolbarNavigationIconResId > 0) {
                toolbar.setNavigationIcon(toolbarNavigationIconResId);
            } else if (toolbarNavigationIconDrawable != null) {
                toolbar.setNavigationIcon(toolbarNavigationIconDrawable);
            }
        }
    }

    /**
     * Set the icon to use for the toolbar's navigation button.
     *
     * @param resId Resource ID of a drawable to set
     */
    public void setToolbarNavigationIcon(@DrawableRes int resId) {
        if (toolbar != null) {
            toolbarNavigationIconResId = resId;
            toolbar.setNavigationIcon(resId);
        }
    }

    /**
     * Set the icon to use for the toolbar's navigation button.
     *
     * @param icon Drawable to set, may be null to clear the icon
     */
    public void setToolbarNavigationIcon(@Nullable Drawable icon) {
        if (toolbar != null) {
            toolbarNavigationIconDrawable = icon;
            toolbar.setNavigationIcon(icon);
        }
    }

    /**
     * Set a logo drawable from a resource id.
     * <p>
     * <p>This drawable should generally take the place of title text. The logo cannot be
     * clicked. Apps using a logo should also supply a description using
     *
     * @param resId ID of a drawable resource
     */
    public void setToolbarLogo(@DrawableRes int resId) {
        if (toolbar != null) {
            toolbar.setLogo(resId);
        }
    }

    /**
     * Set a logo drawable.
     * <p>
     * <p>This drawable should generally take the place of title text. The logo cannot be
     * clicked. Apps using a logo should also supply a description using
     *
     * @param drawable Drawable to use as a logo
     */
    public void setToolbarLogo(@NonNull Drawable drawable) {
        if (toolbar != null) {
            toolbar.setLogo(drawable);
        }
    }

    /**
     * Set the title of this toolbar.
     * <p>
     * <p>A title should be used as the anchor for a section of content. It should
     * describe or name the content being viewed.</p>
     *
     * @param resId Resource ID of a string to set as the title
     */
    public void setToolbarTitle(@StringRes int resId) {
        setToolbarTitle(getText(resId));
    }

    /**
     * Set the title of this toolbar.
     * <p>
     * <p>A title should be used as the anchor for a section of content. It should
     * describe or name the content being viewed.</p>
     *
     * @param title Title to set
     */
    public void setToolbarTitle(@NonNull CharSequence title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    /**
     * Sets the text color of the title, if present.
     *
     * @param color The new text color in 0xAARRGGBB format
     */
    public void setToolbarTitleTextColor(@ColorInt int color) {
        if (toolbar != null) {
            toolbar.setTitleTextColor(color);
        }
    }

    public void setToolbarBackgroundResource(@DrawableRes int resId) {
        if (toolbar != null) {
            toolbar.setBackgroundResource(resId);
        }
    }

    public void setToolbarBackgroundColor(@ColorInt int color) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(color);
        }
    }

    /**
     * Set the subtitle of this toolbar.
     * <p>
     * <p>Subtitles should express extended information about the current content.</p>
     *
     * @param resId String resource ID
     */
    public void setToolbarSubtitle(@StringRes int resId) {
        setToolbarSubtitle(getText(resId));
    }

    /**
     * Set the subtitle of this toolbar.
     * <p>
     * <p>Subtitles should express extended information about the current content.</p>
     *
     * @param subtitle Subtitle to set
     */
    public void setToolbarSubtitle(CharSequence subtitle) {
        if (!TextUtil.isEmpty(subtitle) && toolbar != null) {
            toolbar.setSubtitle(subtitle);
        }
    }

    /**
     * Sets the text color of the subtitle, if present.
     *
     * @param color The new text color in 0xAARRGGBB format
     */
    public void setToolbarSubtitleTextColor(@ColorInt int color) {
        if (toolbar != null) {
            toolbar.setSubtitleTextColor(color);
        }
    }

    /**
     * 该方法回调时机为,Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
     * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
     */
    public void onBackPressedSupport() {
        LogUtil.v(TAG, "onBackPressedSupport");
        super.onBackPressed();
    }

    /**
     * 从栈顶开始，寻找FragmentManager以及其所有子栈, 直到找到状态为show & userVisible的Fragment
     *
     * @param fragmentManager 当前activity的fragment管理者
     * @param parentFragment  上一级fragment
     * @return 当前真正可见的fragmetn
     */
    private static BaseFragment getActiveFragment(FragmentManager fragmentManager, BaseFragment
            parentFragment) {
        @SuppressLint("RestrictedApi") List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList == null) {
            return parentFragment;
        }
        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof BaseFragment) {
                if (fragment.isResumed() && !fragment.isHidden() && fragment.getUserVisibleHint()) {
                    return getActiveFragment(fragment.getChildFragmentManager(), (BaseFragment)
                            fragment);
                }
            }
        }
        return parentFragment;
    }

    /**
     * Dispatch the back-event. Priority of the top of the stack of Fragment
     */
    boolean dispatchBackPressedEvent(BaseFragment activeFragment) {
        if (activeFragment != null) {
            boolean result = activeFragment.onBackPressedSupport();
            if (result) {
                return true;
            }

            Fragment parentFragment = activeFragment.getParentFragment();
            if (dispatchBackPressedEvent((BaseFragment) parentFragment)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取Activity要显示的内容布局的资源文件ID
     *
     * @return 布局文件资源ID
     */
    @LayoutRes
    protected abstract int getContentViewId();

    public void startActivity(@NonNull Class<?> cls) {
        final Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startActivity(@NonNull Class<?> cls, Bundle extras) {
        final Intent intent = new Intent(this, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void startActivity(@NonNull String action, @NonNull Uri uri) {
        final Intent intent = new Intent(action, uri);
        startActivity(intent);
    }

    public void startActivity(@NonNull Class<?> cls, @NonNull String extraName, Serializable
            extraValue) {
        final Intent intent = new Intent(this, cls);
        intent.putExtra(extraName, extraValue);
        startActivity(intent);
    }

    public void startActivity(@NonNull Class<?> cls, @NonNull String extraName, Parcelable
            extraValue) {
        final Intent intent = new Intent(this, cls);
        intent.putExtra(extraName, extraValue);
        startActivity(intent);
    }

    public void startActivityForResult(@NonNull Class<?> cls, int requestCode) {
        final Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 获取通过Intent对象传递过来的参数
     *
     * @param name 参数名称
     */
    public final String getStringExtra(@NonNull String name) {
        Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
        return intent.getStringExtra(name);
    }

    /**
     * 获取通过Intent对象传递过来的参数
     *
     * @param name         Extra参数名称
     * @param defaultValue 没有在intent或bundle对象中找到时设置的默认值
     */
    public final String getStringExtra(@NonNull String name, String defaultValue) {
        String extra = getStringExtra(name);
        if (extra == null) {
            return defaultValue;
        }
        return extra;
    }

    /**
     * 获取通过Intent对象传递过来的参数
     *
     * @param name 参数名称
     */
    public final boolean getBooleanExtra(@NonNull String name) {
        Intent intent = getIntent();
        return intent != null && intent.getBooleanExtra(name, false);
    }

    /**
     * 获取通过Intent对象传递过来的参数
     *
     * @param name         Extra参数名称
     * @param defaultValue 没有在intent或bundle对象中找到时设置的默认值
     */
    public final boolean getBooleanExtra(@NonNull String name, boolean defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        return intent.getBooleanExtra(name, defaultValue);
    }

    /**
     * 获取通过Intent对象传递过来的参数
     *
     * @param name 参数名称
     */
    public final int getIntExtra(@NonNull String name) {
        Intent intent = getIntent();
        if (intent == null) {
            return 0;
        }
        return intent.getIntExtra(name, 0);
    }

    /**
     * 获取通过Intent对象传递过来的参数
     *
     * @param name         Extra参数名称
     * @param defaultValue 没有在intent或bundle对象中找到时设置的默认值
     */
    public final int getIntExtra(@NonNull String name, int defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        return intent.getIntExtra(name, defaultValue);
    }

    public final short getShortExtra(@NonNull String name) {
        final Intent intent = getIntent();
        if (intent == null) {
            return 0;
        }
        return intent.getShortExtra(name, (short) 0);
    }

    public final int getShortExtra(@NonNull String name, short defaultValue) {
        final Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        return intent.getShortExtra(name, defaultValue);
    }

    public final long getLongExtra(@NonNull String name) {
        final Intent intent = getIntent();
        if (intent == null) {
            return 0L;
        }
        return intent.getLongExtra(name, 0L);
    }

    public final long getLongExtra(@NonNull String name, long defaultValue) {
        final Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        return intent.getLongExtra(name, defaultValue);
    }

    /**
     * 获取通过Intent对象传递过来的参数
     *
     * @param name 参数名称
     */
    public final float getFloatExtra(@NonNull String name) {
        Intent intent = getIntent();
        if (intent == null) {
            return 0.0f;
        }
        return getFloatExtra(name, 0.0f);
    }

    /**
     * 获取通过Intent对象传递过来的参数
     *
     * @param name         Extra参数名称
     * @param defaultValue 没有在intent或bundle对象中找到时设置的默认值
     */
    public final float getFloatExtra(@NonNull String name, float defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        return getFloatExtra(name, defaultValue);
    }

    /**
     * 获取通过Intent对象传递过来的参数
     *
     * @param name 参数名称
     */
    public final double getDoubleExtra(@NonNull String name) {
        Intent intent = getIntent();
        if (intent == null) {
            return 0.0;
        }
        return intent.getDoubleExtra(name, 0.0);
    }

    /**
     * 获取通过Intent对象传递过来的参数
     *
     * @param name Extra参数名称
     */
    public final double getDoubleExtra(@NonNull String name, double defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        return intent.getDoubleExtra(name, defaultValue);
    }

    public final <T extends Parcelable> T getParcelableExtra(@NonNull String name) {
        final Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
        return intent.getParcelableExtra(name);
    }

    public final Parcelable[] getParcelableArrayExtra(@NonNull String name) {
        final Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
        return intent.getParcelableArrayExtra(name);
    }

    public final ArrayList<Parcelable> getParcelableArrayListExtra(@NonNull String name) {
        final Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
        return intent.getParcelableArrayListExtra(name);
    }

    public final ArrayList<String> getStringArrayListExtra(@NonNull String name) {
        final Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
        return intent.getStringArrayListExtra(name);
    }

    public final <T extends Serializable> T getSerializableExtra(@NonNull String name) {
        final Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
        return (T) intent.getSerializableExtra(name);
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public final void showShortToast(@NonNull CharSequence text) {
        ToastUtils.showShortToast(this, text);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public final void showShortToast(int resId) {
        ToastUtils.showShortToast(this, resId);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public final void showLongToast(@NonNull CharSequence text) {
        ToastUtils.showLongToast(this, text);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public final void showLongToast(int resId) {
        ToastUtils.showLongToast(this, resId);
    }

    /**
     * 显示加载进度圈
     */
    public void showLoading() {
        if (mLoadingView == null) {
            mLoadingView = new UploadingView(this);
        }
        mLoadingView.show();
    }

    public void showLoading(@StringRes int resId) {
        if (mLoadingView == null) {
            mLoadingView = new UploadingView(this, getString(resId));
        }
        mLoadingView.show();
    }

    public void showLoading(String content) {
        if (mLoadingView == null) {
            mLoadingView = new UploadingView(this, content);
        }
        mLoadingView.show();
    }

    /**
     * 隐藏加载进度圈
     */
    public void hideLoading() {
        if (mLoadingView != null && mLoadingView.isShowing()) {
            mLoadingView.dismiss();
        }
    }


    @Override
    public boolean isHostDestroyed() {
        return isFinishing();
    }

    /**
     * 是否需要统一初始化状态栏
     *
     * @return true-->透明状态栏,false-->不处理
     */
    protected boolean initStatusBar() {
        return true;
    }

    /**
     * 是否是亮色模式
     *
     * @return true:亮色模式;fasle:默认不处理
     */
    protected boolean isLightMode() {
        return true;
    }

    /**
     * 收到{@link EventBus}消息事件回调方法
     *
     * @param what 消息标识
     * @param data 消息体
     */
    protected void onMessageReceived(EventType what, Object data) {

    }


    /**
     * 使用{@link EventBus}发送消息
     *
     * @param what 消息标识
     * @param data 消息体
     */
    protected final void sendMessage(EventType what, @NonNull Object data) {
        final MessageEvent messageEvent = new MessageEvent(what,data);
        EventManager.post(messageEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMessageEvent(MessageEvent event) {
        onMessageReceived(event.type, event.data);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(@FloatRange(from = 0.0,to = 1.0) float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 状态栏透明(Android5.0以上)
     *
     * @param context 上下文
     */
    protected void transparentStatusbar(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            Window window = context.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
}