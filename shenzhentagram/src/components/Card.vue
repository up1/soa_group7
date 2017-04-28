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
          <p class="subtitle is-6"><router-link :to="{name: 'users', params: { userId: this.post.user.id }}">@{{this.post.user.username}}</router-link></p>
        </div>
      </div>

      <div class="content">
        <p v-show="!editing">{{this.post.caption}}</p>
        <div v-show="editing" class="field">
          <p class="control">
            <input class="input" type="text" placeholder="Add a caption..."
                   :value="post.caption"
                   v-focus="editing"
                   @keyup.esc="cancelEdit"
                   @blur="doneEdit">
          </p>
        </div>
        <small>{{this.post.created_at | moment("MMM D, YYYY, h:mm A")}}</small>
      </div>

      <div v-if="this.post.comments.length > 0" class="content">
        <card-comment v-for="comment in this.post.comments" :key="comment.id" :comment="comment"></card-comment>
      </div>
    </div>

      <footer class="card-footer">
        <a href="" class="footer-item icon is-medium"><i class="fa fa-gratipay fa-2x" aria-hidden="true"></i></a>
        <span class="footer-item">
          <form v-on:submit.prevent="doComment">
            <div class="field">
              <p class="control">
                <input class="input is-medium is-borderless" type="text" v-model="form.comment" placeholder="Add a comment...">
              </p>
            </div>
          </form>
        </span>
      <a v-show="post.userId == this.$auth.user().id" v-on:click="active = true" class="footer-item icon"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></a>
    </footer>

    <edit-modal v-on:hide="hideModal" v-on:edit="edit" v-bind:active="active"></edit-modal>
  </div>
</template>

<script type="text/babel">
  import EditModal from './EditModal'
  import CardComment from './CardComment'
  import { focus } from 'vue-focus'
  export default {
    props: ['post'],
    components: {
      CardComment, EditModal
    },
    directives: { focus: focus },
    data () {
      return {
        editing: false,
        active: false,
        form: {
          comment: ''
        }
      }
    },
    created () {
      this.$store.dispatch('fetchComment', this.post.id)
    },
    methods: {
      edit () {
        this.editing = true
      },
      doneEdit (e) {
        const value = e.target.value.trim()
        console.log(value)

        this.$store.dispatch('editCaption', {
          value: {caption: value},
          id: this.post.id
        })
        this.editing = false
      },
      cancelEdit () {
        this.editing = false
      },
      hideModal () {
        this.active = false
      },
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
            console.error('Something wrong in Card.vue -> doComment();')
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
  .card-footer {
    padding: 0.25rem 0rem 0.25rem 2rem;
  }
  .card-footer .footer-item:last-child {
    margin-left: auto;
  }
  .footer-item {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .icon {
    padding-top: 0.75rem;
  }
  .is-borderless {
    border: none;
    box-shadow: none;
  }
</style>
