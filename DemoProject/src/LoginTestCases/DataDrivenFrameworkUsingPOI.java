package LoginTestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DataDrivenFrameworkUsingPOI {

	static List<String> userNameList = new ArrayList<String>();
	static List<String> passwordList = new ArrayList<String>();

	public void readExcel() throws IOException {

		FileInputStream fileInputStream = new FileInputStream("C:\\Users\\nagar\\Downloads\\TestXSSF.xlsx");
		Workbook workbook = new XSSFWorkbook(fileInputStream);

		Sheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row rowvalue = rowIterator.next();

			Iterator<Cell> columnIterator = rowvalue.iterator();
			int i = 2;
			while (columnIterator.hasNext()) {
				if (i % 2 == 0) {
					userNameList.add(columnIterator.next().getStringCellValue());
				} else {
					passwordList.add(columnIterator.next().getStringCellValue());
				}
				i++;
			}

		}
	}

	public void login(String UName, String PWord) {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://opensource-demo.orangehrmlive.com/");

		WebElement userName = driver.findElement(By.id("txtUsername"));
		userName.sendKeys(UName);
		WebElement password = driver.findElement(By.id("txtPassword"));
		password.sendKeys(PWord);
		WebElement loginButton = driver.findElement(By.id("btnLogin"));
		loginButton.click();
		driver.quit();
	}

	public void executescript() {

		for (int i = 0; i < userNameList.size(); i++) {
			login(userNameList.get(i), passwordList.get(i));
		}
	}

	public static void main(String[] args) throws IOException {

		DataDrivenFrameworkUsingPOI dataDrivenFrameworkUsingPOI = new DataDrivenFrameworkUsingPOI();
		dataDrivenFrameworkUsingPOI.readExcel();
		System.out.println("UserNmaelist " + userNameList);
		System.out.println("PasswordList " + passwordList);
		dataDrivenFrameworkUsingPOI.executescript();

	}

}
