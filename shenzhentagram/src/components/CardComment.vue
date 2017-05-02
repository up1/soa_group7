<!-- TODO implement https://github.com/EvanHahn/HumanizeDuration.js for duration -->

<template>
  <article class="media comment">
    <figure class="media-left">
      <figure class="image is-48x48">
        <img v-if="this.comment.user.profile_picture != null" class="profile-img" :src="'https://storage.googleapis.com/shenzhentagram-avatar/' + this.comment.user.profile_picture">
        <img v-else class="profile-img" src="http://bulma.io/images/placeholders/96x96.png" alt="Image">
      </figure>
    </figure>
    <div class="media-content">
      <div class="content">
        <p>
          <strong>{{this.comment.user.display_name}}</strong> <small><router-link :to="{name: 'users', params: { userId: this.comment.user.id }}">@{{this.comment.user.username}}</router-link></small> <small>{{this.comment.createdAt | moment("MMM D, YYYY, h:mm A")}}</small>
          <br>
          <span v-show="!editingComment">{{this.text}}</span>
          <div v-show="editingComment" class="field">
            <p class="control">
              <input class="input" type="text" placeholder="Add a caption..."
                     v-model="form.comment"
                     v-focus="editingComment"
                     @keyup.esc="cancelEditComment"
                     @blur="updateComment">
            </p>
          </div>
        </p>
      </div>
    </div>
    <div v-show="comment.userId == this.$auth.user().id" class="media-right">
      <div class="media-right edit-button">
        <a v-on:click="showCommentModal"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></a>
      </div>
    </div>
  </article>
</template>

<script type="text/babel">
  import { focus } from 'vue-focus'
  export default {
    props: ['comment', 'editingComment'],
    directives: { focus: focus },
    data () {
      return {
        text: '',
        form: {
          comment: ''
        }
      }
    },
    created () {
      this.text = this.comment.text
      this.form.comment = this.comment.text
    },
    methods: {
      showCommentModal () {
        this.$parent.showCommentModal(this.comment.id)
      },
      updateComment () {
        // Trim the comment
        this.form.comment = this.form.comment.replace(/^\s+|\s+$/g, '')

        if (this.form.comment === '') {
          console.log('empty comment')
          this.$parent.doneEditComment()
          return
        }

        if (this.comment.text === this.form.comment) {
          console.log(`nothing change on editting => ${this.comment.id}`)
          this.$parent.doneEditComment()
          return
        }

        this.$store.dispatch('editComment', {postId: this.$parent.post.id, commentId: this.comment.id, text: this.form.comment})
        .then(
          // Success
          () => {
            console.log('Update comment complete')
          },
          // Error
          () => {
            console.error('Something wrong in CardComment.vue -> doneEditComment(); (aka updateComment)')
            this.text = this.comment.text
          }
        )

        // hot-update comment
        this.text = this.form.comment
        this.$parent.doneEditComment()
      },
      cancelEditComment () {
        this.$parent.cancelEditComment()
      }
    }
  }
</script>

<style lang="scss" scoped>
  .edit-button {
    i.fa.fa-ellipsis-v {
      width: 20px;
    }
  }
</style>
