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
        <div v-show="post.userId == this.$auth.user().id" class="media-right">
          <a v-on:click="active = true"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></a>
        </div>
      </div>

      <div class="content">
        <p v-show="!editing">{{this.caption}}</p>
        <div v-show="editing" class="field">
          <p class="control">
            <input class="input" type="text" placeholder="Add a caption..."
                   :value="post.caption"
                   v-focus="editing"
                   @keyup.esc="cancelEdit"
                   @blur="doneEdit">
          </p>
        </div>
        <router-link :to="{name: 'post-single', params: {postId: this.post.id}}" class="is-primary"><small>{{this.post.created_at | moment("MMM D, YYYY, h:mm A")}}</small></router-link>
      </div>

      <div v-if="this.post.comment_count > 0" class="content">
        <card-comment v-for="comment in this.post.comments" :key="comment.id" :comment="comment" v-bind:editingComment="comment.id === targetCommentId && editingComment"></card-comment>
      </div>
    </div>

    <footer class="card-footer">
      <a class="footer-item icon is-medium left" v-on:click="activeReactionModal = true">
        <div v-if="!reacted()">
          <i class="fa fa-gratipay fa-2x reaction" aria-hidden="true"></i>
        </div>
        <div v-else>
          <h2 class="subtitle is-3 is-emoji"><emoji :emoji="reacted().reaction | to_emoji" set="emojione" :size="24"></emoji></h2>
        </div>
      </a>
      <a class="footer-item left" v-on:click="activeReactionDataModal = true">
        <span>{{this.post.reactions}}</span>
      </a>
      <span class="footer-item center">
        <form v-on:submit.prevent="doComment">
          <div class="field">
            <p class="control">
              <input class="input is-medium is-borderless" type="text" v-model="form.comment" placeholder="Add a comment...">
            </p>
          </div>
        </form>
      </span>
      <a class="footer-item icon is-medium right" v-on:click="doComment"><i class="fa fa-commenting fa-2x" aria-hidden="true"></i></a>
    </footer>

    <reaction-data-modal v-on:hide="hideModal" v-bind:active="activeReactionDataModal" v-bind:reactions_data="this.post.reactions_data"></reaction-data-modal>
    <edit-modal v-on:hide="hideModal" v-on:edit="edit" v-on:deletePost="deletePost" v-bind:active="active"></edit-modal>
    <reaction-modal v-on:hide="hideModal" v-bind:active="activeReactionModal" v-on:react="react"></reaction-modal>
    <edit-comment-modal v-on:hide="hideCommentModal" v-on:edit="editComment" v-on:delete="deleteComment" v-bind:active="activeCommentModal"></edit-comment-modal>
  </div>
</template>

<script type="text/babel">
  import EditModal from './EditModal'
  import EditCommentModal from './EditCommentModal'
  import CardComment from './CardComment'
  import ReactionDataModal from './ReactionDataModal'
  import ReactionModal from './ReactionModal'
  import { focus } from 'vue-focus'
  import { Emoji } from 'emoji-mart-vue'
  export default {
    props: ['post'],
    components: {
      CardComment, EditModal, EditCommentModal, ReactionModal, ReactionDataModal, Emoji
    },
    directives: { focus: focus },
    data () {
      return {
        caption: '',
        editing: false,
        active: false,
        activeCommentModal: false,
        activeReactionDataModal: false,
        activeReactionModal: false,
        targetCommentId: '',
        editingComment: false,
        form: {
          comment: ''
        }
      }
    },
    filters: {
      to_emoji: function (v) {
        switch (v) {
          case 'like':
            return 'thumbsup'
          case 'love':
            return 'heart'
          case 'haha':
            return 'laughing'
          case 'wow':
            return 'open_mouth'
          case 'sad':
            return 'sob'
          case 'angry':
            return 'rage'
        }
      }
    },
    created () {
      this.caption = this.post.caption

      if (this.post.comment_count > 0) {
        this.$store.dispatch('fetchComment', this.post.id)
      }
      if (this.post.reactions > 0) {
        this.$store.dispatch('fetchReaction', this.post.id)
      }
    },
    methods: {
      edit () {
        this.editing = true
      },
      deletePost () {
        this.$store.dispatch('deletePost', this.post)
      },
      doneEdit (e) {
        const value = e.target.value.trim()

        this.caption = value
        this.$store.dispatch('editCaption', { value: {caption: value}, id: this.post.id }).then(
          // Success
          () => {},
          // Error
          () => {
            console.error('Something wrong in Card.vue -> doneEdit(); -> EditCommentCaption()')
            this.caption = this.post.caption
          }
        )
        this.editing = false
      },
      cancelEdit () {
        this.editing = false
      },
      hideModal (type) {
        if (type === 'edit') {
          this.active = false
        } else if (type === 'react_data') {
          this.activeReactionDataModal = false
        } else {
          this.activeReactionModal = false
        }
      },

      showCommentModal (targetCommentId) {
        this.targetCommentId = targetCommentId
        this.activeCommentModal = true
      },
      hideCommentModal () {
        this.targetCommentId = ''
        this.activeCommentModal = false
      },
      editComment () {
        this.activeCommentModal = false
        this.editingComment = true
      },
      doneEditComment () {
        this.targetCommentId = ''
        this.editingComment = false
      },
      cancelEditComment () {
        this.targetCommentId = ''
        this.editingComment = false
      },
      deleteComment () {
        this.$store.dispatch('deleteComment', {postId: this.post.id, commentId: this.targetCommentId})
        this.hideCommentModal()
      },
      doComment () {
        // Trim the comment
        this.form.comment = this.form.comment.replace(/^\s+|\s+$/g, '')

        if (this.form.comment === '') {
          console.error('Empty comment')
          return
        }

        this.$store.dispatch('addComment', {postId: this.post.id, text: this.form.comment})
        .then(
          // Success
          () => {
            console.log('Comment complete')
            this.form.comment = ''
          },
          // Error
          (e) => {
            console.error(e)
          }
        )
      },
      react (type) {
        console.log(this.reacted())
        if (!this.reacted()) {
          this.$store.dispatch('addReaction', {
            postId: this.post.id,
            reaction: type
          })
        } else {
          if (!this.reactedSame(type)) {
            this.$store.dispatch('editReaction', {
              postId: this.post.id,
              reaction: type
            })
          } else {
            this.$store.dispatch('deleteReaction', {
              postId: this.post.id,
              userId: this.$auth.user().id
            })
          }
        }
      },
      deleteReaction () {
        this.$store.dispatch('deleteReaction', {
          postId: this.post.id,
          userId: this.$auth.user().id
        })
      },
      reacted () {
        let userId = this.$auth.user().id
        for (let reaction of this.post.reactions_data) {
          if (reaction.user_id === userId) {
            return reaction
          }
        }
        return false
      },
      reactedSame (type) {
        let userId = this.$auth.user().id
        for (let reaction of this.post.reactions_data) {
          if (reaction.user_id === userId && reaction.reaction === type) {
            return true
          }
        }
        return false
      }
    }
  }
</script>

<style lang="scss" scoped>
  .card {
    margin: 50px 0;
  }
  .card-footer {
    padding: 0.25rem 1rem;
  }
  .footer-item {
    display: flex;
    align-items: center;
    justify-content: center;

    form {
      width: 100%;
    }

    .reaction {
      margin-right: 10px
    }

    &.left, &.right {
      width: 32px;
      flex: 0 0 32px;
    }

    &.center {
      width: auto;
      flex: 1 1 auto;
    }
  }
  .icon {
    padding-top: 0.75rem;
  }
</style>
