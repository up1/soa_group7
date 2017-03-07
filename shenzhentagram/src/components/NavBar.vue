<template>
  <nav class="nav has-shadow">
    <div class="container">
      <div class="nav-left">
      <span class="nav-item">
        Shenzhentagram
        <!--<img src="http://bulma.io/images/bulma-logo.png" alt="Bulma logo">-->
      </span>
      </div>

      <div class="nav-center">
        <a class="nav-item">
          <p class="control">
            <input class="input" type="text" placeholder="Search...">
          </p>
        </a>
      </div>

      <!-- This "nav-menu" is hidden on mobile -->
      <!-- Add the modifier "is-active" to display it on mobile -->
      <div class="nav-right nav-menu">
        <a class="nav-item">
          <i class="fa fa-user-o fa-2x" aria-hidden="true"></i>
        </a>
      </div>
    </div>
  </nav>
</template>

<script type="application/ecmascript">
  export default {
    data () {
        return {}
    }
  }
</script>
