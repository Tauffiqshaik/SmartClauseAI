class ReportsPage {
  constructor(driver) {
    this.driver = driver;
  }
  async open() {
    console.log('Navigating to ReportsPage');
  }
  async performAction() {
    console.log('Action on ReportsPage');
  }
}
module.exports = ReportsPage;