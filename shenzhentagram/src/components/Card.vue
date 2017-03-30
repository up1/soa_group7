<template>
  <div class="card">
    <div class="card-image">
      <figure class="image is-1by1">
        <img :src="'https://s3-ap-southeast-1.amazonaws.com/shenzhentagram/' + this.post.media" alt="Image">
      </figure>
    </div>
    <div class="card-content">
      <div class="media">
        <div class="media-left">
          <figure class="image is-48x48">
            <img class="profile-img" src="http://bulma.io/images/placeholders/96x96.png" alt="Image">
          </figure>
        </div>
        <div class="media-content">
          <p class="title is-4">John Smith</p>
          <p class="subtitle is-6">@johnsmith</p>
        </div>
      </div>

      <div class="content">
        {{this.post.caption}}
        <br>
        <small>{{this.post.created_at | moment("MMM D, YYYY, h:mm A")}}</small>
      </div>

      <footer class="card-footer">
        <a href="" class="footer-item"><i class="fa fa-gratipay fa-2x" aria-hidden="true"></i></a>
        <span class="footer-item">
          <div class="field">
            <p class="control">
              <input class="input is-medium" type="text" placeholder="Add a comment...">
            </p>
          </div>
        </span>
      </footer>
    </div>
  </div>
</template>

<script type="text/babel">
  export default {
    props: ['post'],
    data () {
      return {}
    }
  }
</script>

<style scoped>
  .card {
    margin: 50px 0;
  }
  .footer-item {
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    padding: 0.75rem 0.75rem 0 0.75rem;
  }
  .input {
    border: none;
    box-shadow: none;
  }
</style>
