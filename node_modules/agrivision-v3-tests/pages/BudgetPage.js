class BudgetPage {
  constructor(driver) {
    this.driver = driver;
  }
  async open() {
    console.log('Navigating to BudgetPage');
  }
  async performAction() {
    console.log('Action on BudgetPage');
  }
}
module.exports = BudgetPage;