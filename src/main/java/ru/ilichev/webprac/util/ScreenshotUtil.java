package ru.ilichev.webprac.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.util.FileCopyUtils;
import ru.ilichev.webprac.annotation.LazyComponent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@LazyComponent
public class ScreenshotUtil {
    private final ApplicationContext ctx;

    @Value("${screenshot.path}")
    private Path path;

    @Autowired
    public ScreenshotUtil(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public void takeScreenShot(String testName) throws IOException {
        File sourceFile = ctx.getBean(TakesScreenshot.class).getScreenshotAs(OutputType.FILE);
        System.out.println(testName + ".png");
        FileCopyUtils.copy(sourceFile, path.resolve(testName + ".png").toFile());
    }

    public byte[] getScreenshot(){
        return ctx.getBean(TakesScreenshot.class).getScreenshotAs(OutputType.BYTES);
    }
}
