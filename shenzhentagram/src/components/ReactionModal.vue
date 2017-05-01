<template>
  <div v-bind:class="{'is-active': active}" class="modal">
    <div class="modal-background" v-on:click="hideModal"></div>
    <div class="modal-content">
      <a class="button is-large is-borderless is-marginless" v-on:click="react('like')">
        <h2 class="subtitle is-3 is-emoji"><emoji emoji="thumbsup" set="emojione" :size="36" :skin="2"></emoji></h2>
      </a>
      <a class="button is-large is-borderless is-marginless" v-on:click="react('love')">
        <h2 class="subtitle is-3 is-emoji"><emoji emoji="heart" set="emojione" :size="36"></emoji>️</h2>
      </a>
      <a class="button is-large is-borderless is-marginless" v-on:click="react('haha')">
        <h2 class="subtitle is-3 is-emoji"><emoji emoji="laughing" set="emojione" :size="36"></emoji></h2>
      </a>
      <a class="button is-large is-borderless is-marginless" v-on:click="react('wow')">
        <h2 class="subtitle is-3 is-emoji"><emoji emoji="open_mouth" set="emojione" :size="36"></emoji></h2>
      </a>
      <a class="button is-large is-borderless is-marginless" v-on:click="react('sad')">
        <h2 class="subtitle is-3 is-emoji"><emoji emoji="sob" set="emojione" :size="36"></emoji></h2>
      </a>
      <a class="button is-large is-borderless is-marginless" v-on:click="react('angry')">
        <h2 class="subtitle is-3 is-emoji"><emoji emoji="rage" set="emojione" :size="36"></emoji></h2>
      </a>
    </div>
    <button class="modal-close" v-on:click="hideModal"></button>
  </div>
</template>

<script type="text/babel">
  import { Emoji } from 'emoji-mart-vue'
  export default {
    props: ['active'],
    data () {
      return {}
    },
    components: {
      Emoji
    },
    methods: {
      hideModal () {
        this.$emit('hide', 'react')
      },
      react (type) {
        this.$emit('hide', 'react')
        this.$emit('react', type)
      }
    }
  }
</script>

<style scoped>
  .button {
    padding: 1.5em;
    background-color: transparent;
  }
  .is-emoji {
    transition: all 300ms ease;
  }
  .is-emoji:hover {
    transform: scale(1.5, 1.5);
  }
  .modal-content {
    display: flex;
    justify-content: center;
  }
</style>
