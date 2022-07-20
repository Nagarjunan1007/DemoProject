package LoginTestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Login {

	String[][] data = null;

	WebDriver driver;

	@DataProvider(name = "Login Data")
	public String[][] dataProvider() throws BiffException, IOException {
		data = getSheet();
		return data;
	}

	public String[][] getSheet() throws BiffException, IOException {

		FileInputStream getFile = new FileInputStream("C:\\Users\\nagar\\Downloads\\TestXL.xls");
		Workbook wrkBook = Workbook.getWorkbook(getFile);
		Sheet sheet = wrkBook.getSheet(0);

		int rows = sheet.getRows();
		int column = sheet.getColumns();

		String target[][] = new String[rows - 1][column];

		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < column; j++) {
				target[i - 1][j] = sheet.getCell(j, i).getContents();

			}
		}
		return target;
	}

	@BeforeTest
	public void wbDriver() {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();

	}

	@AfterTest
	public void quit() {
		driver.quit();

	}

	@Test(dataProvider = "Login Data")

	public void loginWithCorrectUsernameandPassword(String UName, String PWord) throws InterruptedException {

		driver.get("https://opensource-demo.orangehrmlive.com/");

		WebElement userName = driver.findElement(By.id("txtUsername"));
		userName.sendKeys(UName);
		WebElement password = driver.findElement(By.id("txtPassword"));
		password.sendKeys(PWord);
		WebElement loginButton = driver.findElement(By.id("btnLogin"));
		loginButton.click();
		
	}

}
