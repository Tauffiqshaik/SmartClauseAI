class IncomePage {
  constructor(driver) {
    this.driver = driver;
  }
  async open() {
    console.log('Navigating to IncomePage');
  }
  async performAction() {
    console.log('Action on IncomePage');
  }
}
module.exports = IncomePage;