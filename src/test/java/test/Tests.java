package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Tests {
	
	// Configurações
	private WebDriver driver;
	private int timeout;
	// Cabeçalhos
	private By hdrAddCustomerHeader = By.xpath("//div[contains(text(), 'A') and @class='floatL l5']");
	private By hdrCustomersHeader = By.xpath("//div[contains(text(), 'Customers') and @class='floatL l5']");
	private By hdrDeleteModalHeader = By.xpath("//div[contains(@class, 'delete-multiple-confirmation modal')]//h5[text()='Delete']");
	
	// Botões e Links
	private By btnAdd = By .xpath("//a[contains(text()[2], 'Add Customer') and contains(@class, 'btn btn-default')]");
	private By btnSave = By.xpath("//button[@id='form-button-save']");
	private By btnSaveAndGo = By.xpath("//button[@id='save-and-go-back-button']");
	private By btnClearSearch = By.xpath("//i[contains(@class, 'fa fa-times el el-remove clear-search')]");	
	private By btnDelete = By.xpath("//a[contains(@class, 'btn btn-outline-dark delete-selected-button')]"); 	
	private By btnConfirmDelete = By.xpath("//button[contains(@class, 'btn btn-danger delete-multiple-confirmation-button')]");
	
	// Combos
	private By cmbSelectVersion = By.xpath("//select[@id='switch-version-select']");
	
	// Caixa de seleção
	private By chkItem = By.xpath("//input[contains(@class, 'select-row')]");
	
	// Campos
	private By txtName = By.xpath("//input[@id='field-customerName']");
	private By txtLastName = By.xpath("//input[@id='field-contactLastName']");
	private By txtContactFirstName = By.xpath("//input[@id='field-contactFirstName']");
	private By txtPhone = By.xpath("//input[@id='field-phone']");
	private By txtAddressLine1 = By.xpath("//input[@id='field-addressLine1']");
	private By txtAddressLine2 = By.xpath("//input[@id='field-addressLine2']");
	private By txtCity = By.xpath("//input[@id='field-city']");
	private By txtState = By.xpath("//input[@id='field-state']");
	private By txtPostalCode = By.xpath("//input[@id='field-postalCode']");
	private By txtCountry = By.xpath("//input[@id='field-country']");
	private By lblSelectFromEmployer = By.xpath("//span[text()='Select from Employeer']");
	private By txtFromEmployeer = By.xpath("//div[@class='chosen-search']/input");
	private By txtCreditLimit = By.xpath("//input[@id='field-creditLimit']");
	private By txtSearch = By.xpath("//input[@name='customerName']");
	
	// Mensagens
	private By lblSuccessMessage = By.xpath("//div[@id='report-success']/p");
	private By lblAlertMessage = By.cssSelector(".alert > span:nth-child(4)");
	private By lblDeleteConfirm = By.xpath("//span[@data-growl='message']/p");
	private By lblDelete = By.xpath("//div[contains(@class, 'delete-multiple-confirmation modal')]//p[@class='alert-delete-multiple-one']");
    
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.grocerycrud.com/demo/bootstrap_theme");
		timeout = 60;
	}

	@Test
	public void Test1() throws Exception {
		this.SelectVersion();
		
		driver.findElement(btnAdd).click();       
		
		this.WebDriverWaitElement(hdrAddCustomerHeader);
		
		this.FillData();
		
		driver.findElement(btnSave).click();
		
		this.ValidateInsert(lblSuccessMessage);
	}

	@Test
	public void Test2() throws Exception {
		this.SelectVersion();
    
		driver.findElement(btnAdd).click();
    
		this.WebDriverWaitElement(hdrAddCustomerHeader);
   
		this.FillData();
 	
 		driver.findElement(btnSaveAndGo).click();

 		this.ValidateInsert(lblAlertMessage);
 	
 		this.SearchItem("Teste Sicredi");
		
		this.DeleteItem();
		
		this.ValidateDeletePopup();
		
		driver.findElement(btnConfirmDelete).click();
				
		this.ValidatedDelete();
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}
    
	// Aguarda o elemento estar acessível e visível na tela
	public void WebDriverWaitElement (By element) {
		new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(element));
		new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(element));		
	}
	
	// Seleciona Versão
	public void SelectVersion() {
		driver.findElement(cmbSelectVersion).click();       
		new Select(driver.findElement(cmbSelectVersion)).selectByVisibleText("Bootstrap V4 Theme");       
	}
   
	// Preenche Dados
	public void FillData(){
		driver.findElement(txtName).sendKeys("Teste Sicredi");
		driver.findElement(txtLastName).sendKeys("Teste");
		driver.findElement(txtContactFirstName).sendKeys("Priscila Fraga");
		driver.findElement(txtPhone).sendKeys("51 9999-9999");
		driver.findElement(txtAddressLine1).sendKeys("Av Assis Brasil,3970");
		driver.findElement(txtAddressLine2).sendKeys("Torre D");
		driver.findElement(txtCity).sendKeys("Porto Alegre");
		driver.findElement(txtState).sendKeys("RS");
		driver.findElement(txtPostalCode).sendKeys("91000-000");
		driver.findElement(txtCountry).sendKeys("Brasil");
		
		driver.findElement(txtCountry).sendKeys(Keys.TAB);
		    	
		driver.findElement(lblSelectFromEmployer).click();
		
		this.WebDriverWaitElement(txtFromEmployeer);
				
		driver.findElement(txtFromEmployeer).sendKeys("Fixter"); 		
		driver.findElement(txtFromEmployeer).sendKeys(Keys.TAB);
		
		driver.findElement(txtCreditLimit).sendKeys("200");        
	}
    
	// Busca Item
	public void SearchItem(String item) {
		this.WebDriverWaitElement(hdrCustomersHeader);
		
		driver.findElement(txtSearch).sendKeys(item);
		
		driver.findElement(txtSearch).sendKeys(Keys.ENTER);
		
		this.WebDriverWaitElement(btnClearSearch);
	}

	// Deleta Ttem
	public void DeleteItem() {
		driver.findElement(chkItem).click();
		
		this.WebDriverWaitElement(btnDelete);
		
		driver.findElement(btnDelete).click();		
	}
	
	// Valida de Cadastro
	public void ValidateInsert(By element) {		
		// Define mensagem de validação
		String message = "Your data has been successfully stored into the database.".trim().toUpperCase();
		
		this.WebDriverWaitElement(element);
		
		// Valida a mensagem
		assert (driver.findElement(element).getText().trim().toUpperCase().contains(message));
	}
    
    
	// Valida a exclusão do Popup
	public void ValidateDeletePopup() {
		String message;
	
		// Define mensagem de validação
		message = "Are you sure that you want to delete this 1 item?".trim().toUpperCase();
	
		this.WebDriverWaitElement(hdrDeleteModalHeader);
	
		// Valida a mensagem
		assert (driver.findElement(lblDelete).getText().trim().toUpperCase().equals(message));
	}
	
	
	// Valida a exclusão
	private void ValidatedDelete() {
		String message;
	
		// Define mensagem de validação
		message = "Your data has been successfully deleted from the database.".trim().toUpperCase();
	
		this.WebDriverWaitElement(lblDeleteConfirm);

		// Valida a mensagem
		assert (driver.findElement(lblDeleteConfirm).getText().trim().toUpperCase().equals(message));
	}
}
