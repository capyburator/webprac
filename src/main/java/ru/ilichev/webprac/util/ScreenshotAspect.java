package ru.ilichev.webprac.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ilichev.webprac.annotation.TakeScreenshot;

import java.io.IOException;

@Aspect
@Component
public class ScreenshotAspect {
    private final ScreenshotUtil screenshotUtil;

    @Autowired
    public ScreenshotAspect(ScreenshotUtil screenshotUtil) {
        this.screenshotUtil = screenshotUtil;
    }

    @After("@annotation(takeScreenshot)")
    public void after(JoinPoint joinPoint, TakeScreenshot takeScreenshot) throws IOException {
        this.screenshotUtil.takeScreenShot(joinPoint.getSignature().getName());
    }
}
