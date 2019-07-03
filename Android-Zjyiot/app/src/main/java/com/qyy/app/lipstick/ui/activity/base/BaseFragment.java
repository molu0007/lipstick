package com.qyy.app.lipstick.ui.activity.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.qyy.app.lipstick.views.ProgressFrameLayout;
import com.qyy.app.lipstick.views.UploadingView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;


/**
 * Fragment的基类
 * Created by 曾丽 on 2017/6/20.
 */
public abstract class BaseFragment extends Fragment implements CallDelegate {
    protected final String TAG = getClass().getSimpleName();
    /**
     * fragment根布局(ProgressFrameLayout)
     */
    private ProgressFrameLayout mRootView;
    /**
     * fragment内容布局
     */
    private View mContentView;
    private Unbinder unbinder;
    protected Toolbar toolbar;
    private int toolbarNavigationIconResId;
    private Drawable toolbarNavigationIconDrawable;
    private TextView tvTitle;
    /**
     * 宿主Activity
     */
    protected AppCompatActivity mActivity;
    /**
     * 加载中进度圈(悬浮)
     */
    private UploadingView mLoadingView;
    /**
     * fragment真正对用户可见的标记
     */
    private boolean mIsSupportVisible;
    private RxPermissions rxPermissions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int contentView = getContentViewId();
        mRootView = (ProgressFrameLayout) inflater.inflate(R.layout.fragment_base, container, false);
        mContentView = inflater.inflate(contentView, container, false);
        mRootView.addView(mContentView);
        unbinder = ButterKnife.bind(this,mRootView);
        EventManager.register(this);
        initToolbar();
        initView(container, savedInstanceState);
        final String[] grantPermissions = getGrantPermissions();
        if (grantPermissions != null && grantPermissions.length > 0) {
            requestPermissions(grantPermissions);
        }
        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final int menuResId = getToolbarMenuResId();
        if (menuResId > 0) {
            menu.clear();
            inflater.inflate(menuResId, menu);
            onToolbarMenuCreated(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onToolbarMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (BuildConfig.DEBUG) {
            LogUtil.v(TAG, "onPause...");
        }
        MobclickAgent.onPause(getActivity());
        if (mIsSupportVisible && isFragmentVisible(this)) {
            dispatchSupportVisible(false, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());

        if (BuildConfig.DEBUG) {
            LogUtil.v(TAG, "onResume...");
        }
        if (!mIsSupportVisible && isFragmentVisible(this)) {
            dispatchSupportVisible(true, false);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        dispatchSupportVisible(!hidden, true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        boolean visibleFlag = isResumed() || (isDetached() && isVisibleToUser);
        if (visibleFlag) {
            if (!mIsSupportVisible && isVisibleToUser) {
                dispatchSupportVisible(true, true);
            } else if (mIsSupportVisible && !isVisibleToUser) {
                dispatchSupportVisible(false, true);
            }
        }
    }

    /**
     * 获取要授予的权限数组，如果指定了权限则会在Fragment的{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}方法中申请运行时权限
     * <p>返回示例 return new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA};</p>
     *
     * @return 要申请授予的权限数组，返回null或数组为空，则不会在{@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}中申请权限
     */
    protected String[] getGrantPermissions() {
        return null;
    }

    /**
     * 授权成功时候的回调
     */
    protected void onPermissionsGranted() {

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
     * 用户拒绝授权时候的回调
     */
    protected void onPermissionsDenied() {

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
     * 申请运行时权限，处理Android 6.0及其以上版本权限
     * <p>示例：requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA)
     * 或requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA})</p>
     *
     * @param permissions 要申请的权限数组
     */
    public final void requestPermissions(@NonNull String... permissions) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(mActivity);
        }
        rxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            onPermissionsGranted();
                        } else {
                            onPermissionsDenied();
                        }
                    }
                });
    }

    /**
     * 申请运行时权限，处理Android 6.0及其以上版本权限
     * <p>示例：requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA)
     * 或requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA})</p>
     *
     * @param permissions 要申请的权限数组
     * @param perCode     权限申请的请求码
     */
    public final void requestPermissions(final int perCode, @NonNull String... permissions) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(mActivity);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(TAGS.UI_LIFECYCLE, "onDestroyView=" + TAG);
        unbinder.unbind();
        EventManager.unregister(this);
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
        if (BuildConfig.DEBUG) {
            LogUtil.d(TAG, "onDetach...");
        }
    }

    /**
     * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    public boolean onBackPressedSupport() {
        LogUtil.v(TAG + "=onBackPressedSupport");
        return false;
    }

    /**
     * Called when the fragment is visible.
     * 当Fragment对用户可见时回调
     * <p>
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    protected void onSupportVisible() {
        LogUtil.v(TAGS.UI_LIFECYCLE, "onSupportVisible=" + TAG);
    }

    /**
     * Called when the fragment is invivible.
     * 当Fragment对用户不可见时回调
     * <p>
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    protected void onSupportInvisible() {
        LogUtil.v(TAGS.UI_LIFECYCLE, "onSupportInvisible=" + TAG);
    }

    /**
     * 初始化控件,选择性复写
     */
    protected void initView(@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    /**
     * 初始化数据,选择性复写
     */
    protected void initData() {

    }

    /**
     * 初始化监听(选择性复写,当ButterKinife不能满足需求时使用)
     */
    protected void initListener() {

    }

    protected void onToolbarNavigationClick() {

    }

    protected void onToolbarMenuCreated(Menu menu) {

    }

    protected void onToolbarMenuItemClick(MenuItem menuItem) {

    }

    /**
     * When the title clicked this method will be invoked.
     */
    protected void onCenterTitleClick() {

    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            mActivity.setSupportActionBar(toolbar);

            // 只有Activity的onCreateOptionsMenu()被调用了, 但是Fragment的并没有被调用.
            setHasOptionsMenu(true);
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
        tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
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
     * 初始化根布局
     *
     * @return 获取根布局ID
     */
    protected abstract int getContentViewId();

    /**
     * @return 获取根布局
     */
    public ProgressFrameLayout getRootView() {
        return mRootView;
    }

    /**
     * 获取内容部分布局
     *
     * @return 内容布局控件
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * Finds a view from {@link #mContentView}
     *
     * @return The view if found or null otherwise.
     */
    @Nullable
    public final <VIEW extends View> VIEW findViewById(@IdRes int resId) {
        return (VIEW) mContentView.findViewById(resId);
    }

    @MenuRes
    protected int getToolbarMenuResId() {
        return 0;
    }

    public void hideToolbarTitle() {
        final ActionBar actionBar = mActivity.getSupportActionBar();
        if (toolbar != null) {
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
        tvTitle.setText(title);
    }

    public void setCenterTitleTextColor(@ColorInt int color) {
        tvTitle.setTextColor(color);
    }

    public void startActivity(@NonNull Class<?> cls) {
        final Intent intent = new Intent(mActivity, cls);
        startActivity(intent);
    }

    public void startActivity(@NonNull Class<?> cls, Bundle extras) {
        final Intent intent = new Intent(mActivity, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void startActivity(@NonNull String action, @NonNull Uri uri) {
        final Intent intent = new Intent(action, uri);
        startActivity(intent);
    }

    public <T extends Parcelable> void startActivity(@NonNull Class<?> cls, @NonNull String extraName, T extraValue) {
        final Intent intent = new Intent(mActivity, cls);
        intent.putExtra(extraName, extraValue);
        startActivity(intent);
    }

    public <T extends Serializable> void startActivity(@NonNull Class<?> cls, @NonNull String extraName, T extraValue) {
        final Intent intent = new Intent(mActivity, cls);
        intent.putExtra(extraName, extraValue);
        startActivity(intent);
    }

    public void startActivity(@NonNull Class<?> cls, @NonNull String extraName, @NonNull String extraValue) {
        final Intent intent = new Intent(mActivity, cls);
        intent.putExtra(extraName, extraValue);
        startActivity(intent);
    }

    public void startActivityForResult(@NonNull Class<?> cls, int requestCode) {
        final Intent intent = new Intent(mActivity, cls);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(@NonNull Class<?> cls, Bundle extras, int requestCode) {
        final Intent intent = new Intent(mActivity, cls);
        intent.putExtras(extras);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(@NonNull String action, @NonNull Uri uri, int requestCode) {
        final Intent intent = new Intent(action, uri);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(@NonNull Class<?> cls, @NonNull String extraName, Parcelable extraValue, int requestCode) {
        final Intent intent = new Intent(mActivity, cls);
        intent.putExtra(extraName, extraValue);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 显示加载进度圈(悬浮)
     */
    public void showUpLoading() {
        if (mLoadingView == null) {
            mLoadingView = new UploadingView(mActivity);
        }
        mLoadingView.show();
    }

    /**
     * 隐藏加载进度圈(悬浮)
     */
    public void hideUpLoading() {
        if (mLoadingView != null && mLoadingView.isShowing()) {
            mLoadingView.dismiss();
        }
    }

    /**
     * 显示空页面
     */
    public void showEmpty() {
        mRootView.showEmpty();
    }

    /**
     * 显示空页面
     *
     * @param tip 空页面提示
     */
    public void showEmpty(String tip) {
        mRootView.showEmpty();
    }

    /**
     * 显示错误页
     *
     * @param errorTextTitle   标题
     * @param errorTextContent 内容
     * @param onClickListener  点击重试
     */
    public void showError(String errorTextTitle, String errorTextContent, View.OnClickListener onClickListener) {
        mRootView.showError(errorTextTitle, errorTextContent, onClickListener);
    }

    /**
     * 显示错误页
     *
     * @param errorImageDrawable 错误图标
     * @param errorTextTitle     标题
     * @param errorTextContent   内容
     * @param onClickListener    点击重试
     */
    public void showError(int errorImageDrawable, String errorTextTitle, String errorTextContent, View.OnClickListener onClickListener) {
        mRootView.showError(errorImageDrawable, errorTextTitle, errorTextContent, onClickListener);
    }


    /**
     * 显示加载中
     */
    public void showLoading() {
        mRootView.showLoading();
    }

    /**
     * 隐藏加载中
     */
    public void hideLoading() {
        mRootView.showContent();
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public final void showShortToast(@NonNull CharSequence text) {
        ToastUtils.showShortToast(mActivity, text);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public final void showShortToast(int resId) {
        ToastUtils.showShortToast(mActivity, resId);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public final void showLongToast(@NonNull CharSequence text) {
        ToastUtils.showLongToast(mActivity, text);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public final void showLongToast(int resId) {
        ToastUtils.showLongToast(mActivity, resId);
    }

    /**
     * fragment是否可见
     *
     * @param fragment  fragment
     * @return true:可见 ,false:被隐藏
     */
    private boolean isFragmentVisible(Fragment fragment) {
        //需要考虑自己和所有的父fragment
        Fragment parentFragment = fragment.getParentFragment();
        while (parentFragment != null) {
            boolean parentVisibel = !parentFragment.isHidden() && parentFragment.getUserVisibleHint();
            //嵌套的fragment,任意父fragment不可见,则返回false
            if (!parentVisibel) {
                return false;
            }
            parentFragment = parentFragment.getParentFragment();
        }
        return !fragment.isHidden() && fragment.getUserVisibleHint();
    }

    /**
     * fragment是否对用户可见
     *
     * @return true-->可见,false-->不可见
     */
    public boolean isSupportVisible() {
        return mIsSupportVisible;
    }

    /**
     * 分发fragment可见的通知
     *
     * @param visible        是否可见
     * @param shouldDispatch 是否要分发
     */
    public void dispatchSupportVisible(boolean visible, boolean shouldDispatch) {
        mIsSupportVisible = visible;

        if (visible) {
            onSupportVisible();
        } else {
            onSupportInvisible();
        }

        if (!shouldDispatch) {
            return;
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        if (fragmentManager != null) {
            @SuppressLint("RestrictedApi") List<Fragment> childFragments = fragmentManager.getFragments();
            if (childFragments != null) {
                for (Fragment child : childFragments) {
                    if (child instanceof BaseFragment && !child.isHidden() && child.getUserVisibleHint()) {
                        BaseFragment childFragment = (BaseFragment) child;
                        childFragment.dispatchSupportVisible(visible, true);
                    }
                }
            }
        }
    }

    @Override
    public boolean isHostDestroyed() {
        return !isAdded();
    }

    /**
     * 收到{@link EventBus}消息事件回调方法
     *
     * @param what  消息标识
     * @param event 消息体
     */
    protected void onMessageReceived(EventType what, Object event) {

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
    @CallSuper
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.d(TAG, "onActivityResult -> requestCode = " +
                requestCode + ",resultCode = " + resultCode + ",Intent data = " + data);
    }
}