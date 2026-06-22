class ProfilePage {
  constructor(driver) {
    this.driver = driver;
  }
  async open() {
    console.log('Navigating to ProfilePage');
  }
  async performAction() {
    console.log('Action on ProfilePage');
  }
}
module.exports = ProfilePage;