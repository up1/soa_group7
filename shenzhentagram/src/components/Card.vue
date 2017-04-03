<template>
  <div class="card">
    <div class="card-image">
      <figure class="image">
        <img :src="'https://storage.googleapis.com/shenzhentagram-posts/' + this.post.media" alt="Image">
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
        <a href="" class="footer-item icon is-medium"><i class="fa fa-gratipay fa-2x" aria-hidden="true"></i></a>
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
    align-items: flex-start;
    justify-content: center;
    padding: 0.2rem 0.75rem;
  }
  .icon {
    padding-top: 0.75rem;
  }
  .input {
    border: none;
    box-shadow: none;
  }
</style>
