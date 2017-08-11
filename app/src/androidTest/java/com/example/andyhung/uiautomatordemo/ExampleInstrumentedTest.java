package com.example.andyhung.uiautomatordemo;

import android.content.Context;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.filters.SdkSuppress;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ExampleInstrumentedTest {

    private static final long DEFAULT_TIMEOUT = 3000;

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.andyhung.uiautomatordemo", appContext.getPackageName());
    }

    @Test
    public void discoveryMusic() throws UiObjectNotFoundException, RemoteException, Execption {
        mDevice.wait(findText("KKbox"), DEFAULT_TIMEOUT).click();
        mDevice.wait(findClassName(CLASS_NAME_EDIT_TEXT), DEFAULT_TIMEOUT).click();
    }

    @Test
    public void playMusic() throws UiObjectNotFoundException, RemoteException, Execption {
        pass;
    }

    @Test
    public void searchMusic() throws UiObjectNotFoundException, RemoteException, Execption {
        pass;
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

}
