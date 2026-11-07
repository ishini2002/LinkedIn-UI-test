package com.ishini.sqa.tests;

import com.ishini.sqa.utils.Config;
import org.testng.annotations.Test;

public class ConfigCheckTest {

    @Test
    public void showConfigInfo() {
        System.out.println("CONFIG username length = " + Config.username().length());
        System.out.println("CONFIG password length = " + Config.password().length());
        System.out.println("CONFIG password has extra space at an end = "
                + !Config.password().equals(Config.password().trim()));
    }
}