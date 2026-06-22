class LogoutPage {
  constructor(driver) {
    this.driver = driver;
  }
  async open() {
    console.log('Navigating to LogoutPage');
  }
  async performAction() {
    console.log('Action on LogoutPage');
  }
}
module.exports = LogoutPage;