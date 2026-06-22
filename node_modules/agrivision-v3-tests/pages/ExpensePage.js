class ExpensePage {
  constructor(driver) {
    this.driver = driver;
  }
  async open() {
    console.log('Navigating to ExpensePage');
  }
  async performAction() {
    console.log('Action on ExpensePage');
  }
}
module.exports = ExpensePage;