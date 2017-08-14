package com.example.andyhung.uiautomatordemo;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.SearchCondition;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ExampleInstrumentedTest {

    private static final long DEFAULT_TIMEOUT = 3000;
    private static final String PHONE_PERMISSION_WATCHER_NAME = "PHONE_PERMISSION_WATCHER";
    private static final String PHOTO_PERMISSION_WATCHER_NAME = "PHOTO_PERMISSION_WATCHER";
    private static final String APP_PERMISSION_WATCHER_NAME = "APP_PERMISSION_WATCHER";
    private UiDevice mDevice;

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();
        addWatcher("acceptPhonePermission");
        addWatcher("acceptPhotoPermission");
        addWatcher("closeAppPermission");
    }

    @After
    public void tearDown() {
        closeWatcher("acceptPhonePermission");
        closeWatcher("acceptPhotoPermission");
        closeWatcher("closeAppPermission");
    }

    //@Test
    public void logonApp() throws UiObjectNotFoundException, RemoteException {
        mDevice.wait(findText("KKBOX"), DEFAULT_TIMEOUT).click();
        mDevice.wait(findClassName("android.widget.Button"), DEFAULT_TIMEOUT);
        UiObject loginButton = mDevice.findObject(new UiSelector().resourceId("com.skysoft.kkbox.android:id/button_login"));
        Assert.assertEquals("登入", loginButton.getText());
        mDevice.wait(findText("登入"), DEFAULT_TIMEOUT).click();
        mDevice.wait(findText("電子信箱或手機號碼"), DEFAULT_TIMEOUT).click();
    }

    @Test
    public void discoveryMusic() throws UiObjectNotFoundException, RemoteException {
        mDevice.wait(findText("KKBOX"), DEFAULT_TIMEOUT).click();
        mDevice.wait(findClassName("android.widget.ImageButton"), DEFAULT_TIMEOUT).click();
        mDevice.wait(findClassName("android.widget.ImageView"), DEFAULT_TIMEOUT).click();
        mDevice.wait(findClassName("android.widget.TextView"), DEFAULT_TIMEOUT);
        UiObject testView = mDevice.findObject(new UiSelector().text("線上精選"));
        Assert.assertEquals("線上精選", testView.getText());
    }

    @Test
    public void searchAndPlayMusic() throws UiObjectNotFoundException, RemoteException {
        mDevice.wait(findText("KKBOX"), DEFAULT_TIMEOUT).click();
        mDevice.wait(findResAndDescription("com.skysoft.kkbox.android", "menu_global_search", "線上搜尋"), DEFAULT_TIMEOUT).click();
        mDevice.wait(findClassNameAndText("android.widget.EditText", "   搜尋"), DEFAULT_TIMEOUT).setText("青花瓷");
        mDevice.pressKeyCode(KeyEvent.KEYCODE_ENTER);
        UiObject resultObject = mDevice.findObject(selectClassNameAndResId("android.widget.FrameLayout", "com.skysoft.kkbox.android:id/layout_swipe"));
	Assert.assertTrue(resultObject.exists());
        if (resultObject.exists()) {
            try {
                resultObject.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private SearchCondition<UiObject2> findText(String text) {
        return Until.findObject(By.text(text));
    }

    private SearchCondition<UiObject2> findClassName(String className) {
        return Until.findObject(By.clazz(className));
    }

    private SearchCondition<UiObject2> findPackageNameAndText(String applicationPackage, String text) {
        return Until.findObject(By.pkg(applicationPackage).text(text));
    }

    private SearchCondition<UiObject2> findResAndDescription(String resourcePackage, String resourceId,
                                                             String contentDescription) {
        return Until.findObject(By.res(resourcePackage, resourceId).desc(contentDescription));
    }

    private SearchCondition<UiObject2> findClassNameAndText(String className, String text) {
        return Until.findObject(By.clazz(className).text(text));
    }

    private UiSelector selectClassNameAndResId(String className, String resId) {
        return new UiSelector().className(className).resourceId(resId);
    }

    private void addWatcher(String watcherName) {
        switch (watcherName) {
            case "acceptPhonePermission":
                UiWatcher phonePermissionWatcher = new UiWatcher() {
                    @Override
                    public boolean checkForCondition() {
                        UiObject alterObject = new UiObject(new UiSelector().text("允許「KKBOX」撥打電話及管理通話嗎？"));
                        if (alterObject.exists()) {
                            UiObject okButton = new UiObject(new UiSelector().className("android.widget.Button").text("允許"));
                            try {
                                okButton.click();
                            } catch (UiObjectNotFoundException e) {
                                e.printStackTrace();
                            }

                            return (alterObject.waitUntilGone(25000));
                        }
                        return false;
                    }
                };
                mDevice.registerWatcher(PHONE_PERMISSION_WATCHER_NAME, phonePermissionWatcher);
                mDevice.runWatchers();
                break;
            case "acceptPhotoPermission":
                UiWatcher photoPermissionWatcher = new UiWatcher() {
                    @Override
                    public boolean checkForCondition() {
                        UiObject alterObject = new UiObject(new UiSelector().text("允許「KKBOX」存取裝置中的相片、媒體和檔案嗎？"));
                        if (alterObject.exists()) {
                            UiObject okButton = new UiObject(new UiSelector().className("android.widget.Button").text("允許"));
                            try {
                                okButton.click();
                            } catch (UiObjectNotFoundException e) {
                                e.printStackTrace();
                            }

                            return (alterObject.waitUntilGone(25000));
                        }
                        return false;
                    }
                };
                mDevice.registerWatcher(PHOTO_PERMISSION_WATCHER_NAME, photoPermissionWatcher);
                mDevice.runWatchers();
                break;
            case "closeAppPermission":
                UiWatcher appPermissionWatcher = new UiWatcher() {
                    @Override
                    public boolean checkForCondition() {
                        UiObject alterObject = new UiObject(new UiSelector().text("請允許KKBOX 取得權限，做為登入時判斷裝置及儲存歌曲資料之用。"));
                        if (alterObject.exists()) {
                            UiObject okButton = new UiObject(new UiSelector().className("android.widget.Button").text("取消"));
                            try {
                                okButton.click();
                            } catch (UiObjectNotFoundException e) {
                                e.printStackTrace();
                            }

                            return (alterObject.waitUntilGone(25000));
                        }
                        return false;
                    }
                };
                mDevice.registerWatcher(APP_PERMISSION_WATCHER_NAME, appPermissionWatcher);
                mDevice.runWatchers();
                break;
        }
    }
    private void closeWatcher(String watcherName) {
        switch (watcherName) {
            case "acceptPhonePermission":
                mDevice.removeWatcher(PHONE_PERMISSION_WATCHER_NAME);
                break;
            case "acceptPhotoPermission":
                mDevice.removeWatcher(PHOTO_PERMISSION_WATCHER_NAME);
                break;
            case "closeAppPermission":
                mDevice.removeWatcher(APP_PERMISSION_WATCHER_NAME);
                break;
        }

    }
}
