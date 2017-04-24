<!-- TODO implement https://github.com/EvanHahn/HumanizeDuration.js for duration -->

<template>
  <article class="media comment">
    <figure class="media-left">
      <figure class="image is-48x48">
        <img class="profile-img" src="http://bulma.io/images/placeholders/96x96.png" alt="Image">
      </figure>
    </figure>
    <div class="media-content">
      <div class="content">
        <p>
          <strong>{{this.comment.user.display_name}}</strong> <small>@{{this.comment.user.username}}</small> <small>{{this.comment.createdAt | moment("MMM D, YYYY, h:mm A")}}</small>
          <br>
          {{this.comment.text}}
        </p>
      </div>
    </div>
    <!-- <div class="media-right">
      <button class="delete"></button>
    </div> -->
  </article>
</template>

<script type="text/babel">
  export default {
    props: ['comment'],
    data () {
      return {}
    }
  }
</script>
