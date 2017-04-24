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
          <p class="title is-4">{{this.post.user.display_name}}</p>
          <p class="subtitle is-6">@{{this.post.user.username}}</p>
        </div>
      </div>

      <div class="content">
        {{this.post.caption}}
        <br>
        <small>{{this.post.created_at | moment("MMM D, YYYY, h:mm A")}}</small>
      </div>

      <div v-if="comments.length > 0" class="content">
        <card-comment v-for="comment in comments" :key="comment.id" :comment="comment"></card-comment>
      </div>

      <footer class="card-footer">
        <a href="" class="footer-item icon is-medium"><i class="fa fa-gratipay fa-2x" aria-hidden="true"></i></a>
        <span class="footer-item">
          <form v-on:submit.prevent="doComment">
            <div class="field">
              <p class="control">
                <input class="input is-medium" type="text" v-model="form.comment" placeholder="Add a comment...">
              </p>
            </div>
          </form>
        </span>
      </footer>
    </div>
  </div>
</template>

<script type="text/babel">
  import CardComment from './CardComment'
  export default {
    props: ['post'],
    components: {
      CardComment
    },
    data () {
      return {
        comments: [],
        form: {
          comment: ''
        }
      }
    },
    created () {
      this.$http.get('posts/' + this.post.id + '/comments')
      .then(
        // Success
        (response) => {
          this.comments = response.body
        },
        // Error
        () => {
          console.error('Something wrong in Card.vue -> created -> fetchComments();')
        }
      )
    },
    methods: {
      doComment () {
        // Trim the comment
        this.form.comment = this.form.comment.replace(/^\s+|\s+$/g, '')

        if (this.form.comment === '') {
          console.error('Empty comment')
          return
        }

        this.$http.post('posts/' + this.post.id + '/comments', { text: this.form.comment })
        .then(
          // Success
          () => {
            console.log('Comment complete')
            // FIXME POST /posts/{id}/comments need to return the new created comment
            this.comments.push({
              text: this.form.comment,
              createdAt: new Date(),
              user: this.$auth.user()
            })
            this.form.comment = ''
          },
          // Error
          () => {
            console.error('Something wrong in PostForm.vue -> doPost();')
            this.error = 'Something wrong'
            this.showModal()
          }
        )
      }
    }
  }
</script>

<style lang="scss" scoped>
  .card {
    margin: 50px 0;
  }
  .footer-item {
    display: flex;
    width: 100%;
    align-items: flex-start;
    padding: 0.2rem 0;

    form {
      width: 100%;
    }
  }
  .icon {
    padding-top: 0.75rem;
  }
  .input {
    border: none;
    box-shadow: none;
  }
</style>
