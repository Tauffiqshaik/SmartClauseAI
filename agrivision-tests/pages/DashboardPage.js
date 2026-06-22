class DashboardPage {
  constructor(driver) {
    this.driver = driver;
  }
  async open() {
    console.log('Navigating to DashboardPage');
  }
  async performAction() {
    console.log('Action on DashboardPage');
  }
}
module.exports = DashboardPage;