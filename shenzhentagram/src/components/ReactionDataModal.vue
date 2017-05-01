<template>
  <div class="modal" v-bind:class="{'is-active': active}">
    <div class="modal-background" v-on:click="hideModal"></div>
    <div class="modal-content">
      <div class="card">
        <header class="card-header">
          <p class="card-header-title">
            Reactions
          </p>
        </header>
        <div class="card-content">
          <div class="content">
            <div class="reaction list" v-for="reaction in reactions_data">
              <div>
                <figure class="image is-32x32">
                  <img class="profile-img" src="http://bulma.io/images/placeholders/96x96.png" alt="Image">
                </figure>
                <div class="name">
                  <p class="name is-bold">username</p>
                  <p class="name">displayname</p>
                </div>
              </div>
              <h1 class="title is-5 is-emoji"><emoji :emoji="reaction.reaction | to_emoji" set="emojione" :skin="2"></emoji></h1>
            </div>
          </div>
        </div>
      </div>
    </div>
    <button class="modal-close" v-on:click="hideModal"></button>
  </div>
</template>

<script type="text/babel">
  import { Emoji } from 'emoji-mart-vue'
  export default {
    props: ['active', 'reactions_data'],
    components: {
      Emoji
    },
    data () {
      return {}
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
    methods: {
      hideModal () {
        this.$emit('hide', 'react_data')
      }
    }
  }
</script>

<style scoped>
  .content h1 {
    margin-bottom: 0;
    margin-top: 0 !important;
  }
  .card-content {
    max-height: 378px;
    overflow-y: auto;
    overflow-x: hidden;
    padding: 0;
    -webkit-overflow-scrolling: touch;
  }
  .reaction {
    padding: 1rem 1.5rem;
    border: solid 1px #e6e6e6;
    background-color: #fff;
  }

  .list {
    align-items: center;
    display: flex;
    flex-direction: row;
    flex-shrink: 0;
  }
  .list > div {
    align-items: center;
    margin-right: 0;
    white-space: nowrap;
    width: 100%;
    flex-direction: row;
    display: flex;
  }
  .list .image {
    margin-right: 10px;
  }
  .list .name {
    font-size: 16px;
    margin-bottom: 0;
  }
</style>
