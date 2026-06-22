class LoginPage {
  constructor(driver) {
    this.driver = driver;
  }
  async open() {
    console.log('Navigating to LoginPage');
  }
  async performAction() {
    console.log('Action on LoginPage');
  }
}
module.exports = LoginPage;