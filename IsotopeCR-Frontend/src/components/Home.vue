<template>
<div class="home">
  <div class="jumbotron jumbotron-fluid">
    <div class="container">
      <h1 class="display-4"> Welcome to the Isotope Car Repair Shop</h1>
      <h2 class="subtitle"> Make sure you check out our services below</h2>
    </div>
  </div>
  <!-- Services-->
  <section class="page-section" id="services">
      <div class="container">
          <div class="text-center">
              <h2 class="section-heading text-uppercase">Services</h2>
          </div>
          <div class="row text-center">
              <div class="col-md-4">
                  <span class="fa-stack fa-4x">
                      <i class="fas fa-circle fa-stack-2x text-primary"></i>
                      <i class="fas fa-shopping-cart fa-stack-1x fa-inverse"></i>
                  </span>
                  <h4 class="my-3">Oil Change</h4>
                  <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima maxime quam architecto quo inventore harum ex magni, dicta impedit.</p>
              </div>
              <div class="col-md-4">
                  <span class="fa-stack fa-4x">
                      <i class="fas fa-circle fa-stack-2x text-primary"></i>
                      <i class="fas fa-laptop fa-stack-1x fa-inverse"></i>
                  </span>
                  <h4 class="my-3">Roadside Assitance</h4>
                  <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima maxime quam architecto quo inventore harum ex magni, dicta impedit.</p>
              </div>
              <div class="col-md-4">
                  <span class="fa-stack fa-4x">
                      <i class="fas fa-circle fa-stack-2x text-primary"></i>
                      <i class="fas fa-lock fa-stack-1x fa-inverse"></i>
                  </span>
                  <h4 class="my-3">Car & Truck Care</h4>
                  <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima maxime quam architecto quo inventore harum ex magni, dicta impedit.</p>
              </div>
          </div>
          <div class="row text-center" id = "servicesR2">
              <div class="col-md-4">
                  <span class="fa-stack fa-4x">
                      <i class="fas fa-circle fa-stack-2x text-primary"></i>
                      <i class="fas fa-shopping-cart fa-stack-1x fa-inverse"></i>
                  </span>
                  <h4 class="my-3">Oil Change</h4>
                  <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima maxime quam architecto quo inventore harum ex magni, dicta impedit.</p>
              </div>
              <div class="col-md-4">
                  <span class="fa-stack fa-4x">
                      <i class="fas fa-circle fa-stack-2x text-primary"></i>
                      <i class="fas fa-laptop fa-stack-1x fa-inverse"></i>
                  </span>
                  <h4 class="my-3">Roadside Assitance</h4>
                  <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima maxime quam architecto quo inventore harum ex magni, dicta impedit.</p>
              </div>
              <div class="col-md-4">
                  <span class="fa-stack fa-4x">
                      <i class="fas fa-circle fa-stack-2x text-primary"></i>
                      <i class="fas fa-lock fa-stack-1x fa-inverse"></i>
                  </span>
                  <h4 class="my-3">Car & Truck Care</h4>
                  <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Minima maxime quam architecto quo inventore harum ex magni, dicta impedit.</p>
              </div>
          </div>
      </div>
  </section>
</div>
</template>

<script>
export default {
  name: 'home',
  components: {
  }
}
</script>

<style scoped>
.jumbotron {
  color: white;
  background-image: url('../assets/repair-shop.jpg');
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
</style>