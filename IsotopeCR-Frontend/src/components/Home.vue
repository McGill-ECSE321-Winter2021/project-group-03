<template>
  <div class="home">
    <div class="jumbotron jumbotron-fluid">
      <div class="container">
        <h1 class="display-4">Welcome to the Isotope Car Repair Shop</h1>
        <h2 class="subtitle">Make sure you check out our services below</h2>
      </div>
    </div>
    <!-- Services-->
    <section class="page-section" id="services">
      <div class="container">
        <div class="text-center">
          <h2 class="section-heading text-uppercase">Services</h2>
        </div>
        <div class="row text-center" id="servicesR1">
          <div class="col-md-4">
            <b-icon
              icon="droplet-fill"
              style="width: 80px; height: 80px"
            ></b-icon>
            <h4 class="my-3">Oil Change</h4>
            <p class="text-muted">
              Let our technicians help you choose the oil that’s best for your car. 
              Take advantage of a 15-point inspection, included with your oil change. 
              Service Driven By Experts. 
            </p>
          </div>
          <div class="col-md-4">
            <b-icon icon="tools" style="width: 80px; height: 80px"></b-icon>
            <h4 class="my-3">Roadside Assistance</h4>
            <p class="text-muted">
              Count On Us For High-Quality, 24/7 Towing Services With Our Advanced Equipment. 
              Call Us! We Are Well-Known For Our Exceptional Automotive Hauling & Roadside Assistance Solutions. 
              No Hidden Fees. Direct Billing. Fast Service. Courteous Staff. Experienced Team.
            </p>
          </div>
          <div class="col-md-4">
            <b-icon icon="gear-fill" style="width: 80px; height: 80px"></b-icon>
            <h4 class="my-3">Car & Truck Care</h4>
            <p class="text-muted">
              We will clean vehicles according to company standards or client specifications, which may 
              include performing detail inspections, thoroughly washing, buffing, and waxing exteriors, vacuuming, 
              steaming, and deodorizing interiors, and keeping records related to gas levels and the condition of the vehicle.
            </p>
          </div>
        </div>
        <div class="row text-center" id="servicesR2">
          <div class="col-md-4">
            <b-icon icon="disc-fill" style="width: 80px; height: 80px"></b-icon>
            <h4 class="my-3">Tire Change</h4>
            <p class="text-muted">
              IsotopeCR is Your One-Stop Shop For All Your Tire Installation Needs. Learn More Now! Tire Maintenance.
            </p>
          </div>
          <div class="col-md-4">
            <b-icon
              icon="file-earmark-medical-fill"
              style="width: 80px; height: 80px"
            ></b-icon>
            <h4 class="my-3">Vehicle Diagnostic</h4>
            <p class="text-muted">
              Our technician will determine if your vehicle has issues with its engine, exhaust, transmission, ignition coils,
              oil tank, throttle, and more.
            </p>
          </div>
          <div class="col-md-4">
            <b-icon
              icon="lightning-fill"
              style="width: 80px; height: 80px"
            ></b-icon>
            <h4 class="my-3">Electrical Services</h4>
            <p class="text-muted">
              Electrical layout, design, wiring and installation; Upgrades; Energy audits; Sales, installation and maintenance
              of your car's generator
            </p>
          </div>
        </div>
      </div>
    </section>

    <div class="about-section" id = "about">
      <h1>About</h1>
      <h3 style="color:orange;">Expert auto care for all. </h3>
      <p>We provide the expert auto maintenance you need - for any brand of car, truck or SUV.</p>
    </div>

    <!-- Footer -->
    <footer>
      <div class="container" id="footer">
        <div class="row">
          <div class="col-md-4">
            <span class="copyright">Copyright &copy; blackend.io 2017</span>
          </div>
          <div class="col-md-4">
            <ul class="list-inline social-buttons">
              <li class="list-inline-item">
                <a href="\"
                  ><b-icon
                    icon="twitter"
                    style="width: 20px; height: 20px"
                  ></b-icon
                ></a>
              </li>
              <li class="list-inline-item">
                <a href="\"
                  ><b-icon
                    icon="facebook"
                    style="width: 20px; height: 20px"
                  ></b-icon
                ></a>
              </li>
              <li class="list-inline-item">
                <a href="\"
                  ><b-icon
                    icon="instagram"
                    style="width: 20px; height: 20px"
                  ></b-icon
                ></a>
              </li>
            </ul>
          </div>
          <div class="col-md-4">
            <ul class="list-inline quicklinks">
              <li class="list-inline-item">
                <a href="#">Privacy Policy</a>
              </li>
              <li class="list-inline-item">
                <a @click="logOut()">Terms of Use</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "home",
  components: {},
  methods: {
    logOut: function () {
      this.$cookie.delete("email");
      localStorage.removeItem("loggedIn");
      window.location.href = "/";
    },
  },
};
</script>

<style scoped>
.jumbotron {
  color: white;
  background-image: url("../assets/repair-shop.jpg");
  padding-top: 150px;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: fixed;
  height: 65vh;
}
.jumbotron h1 {
  display: block;
  font-weight: bold;
  font-size: 50px;
  color: white;
}
#servicesR2 {
  padding-top: 2vh;
}
#servicesR1 {
  padding-top: 3vh;
}
#about {
  padding-top: 5vh;
}
#footer {
  padding-top: 5vh;
}
</style>