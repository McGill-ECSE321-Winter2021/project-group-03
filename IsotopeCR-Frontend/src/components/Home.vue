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
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima
              maxime quam architecto quo inventore harum ex magni, dicta
              impedit.
            </p>
          </div>
          <div class="col-md-4">
            <b-icon icon="tools" style="width: 80px; height: 80px"></b-icon>
            <h4 class="my-3">Roadside Assistance</h4>
            <p class="text-muted">
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima
              maxime quam architecto quo inventore harum ex magni, dicta
              impedit.
            </p>
          </div>
          <div class="col-md-4">
            <b-icon icon="gear-fill" style="width: 80px; height: 80px"></b-icon>
            <h4 class="my-3">Car & Truck Care</h4>
            <p class="text-muted">
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima
              maxime quam architecto quo inventore harum ex magni, dicta
              impedit.
            </p>
          </div>
        </div>
        <div class="row text-center" id="servicesR2">
          <div class="col-md-4">
            <b-icon icon="disc-fill" style="width: 80px; height: 80px"></b-icon>
            <h4 class="my-3">Tire Change</h4>
            <p class="text-muted">
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima
              maxime quam architecto quo inventore harum ex magni, dicta
              impedit.
            </p>
          </div>
          <div class="col-md-4">
            <b-icon
              icon="file-earmark-medical-fill"
              style="width: 80px; height: 80px"
            ></b-icon>
            <h4 class="my-3">Vehicle Diagnostic</h4>
            <p class="text-muted">
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima
              maxime quam architecto quo inventore harum ex magni, dicta
              impedit.
            </p>
          </div>
          <div class="col-md-4">
            <b-icon
              icon="lightning-fill"
              style="width: 80px; height: 80px"
            ></b-icon>
            <h4 class="my-3">Electrical Services</h4>
            <p class="text-muted">
              Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima
              maxime quam architecto quo inventore harum ex magni, dicta
              impedit.
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
            <span class="copyright">Copyright &copy; ECSE321W21G03 </span>
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