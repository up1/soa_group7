<template>
  <div v-bind:class="{'is-active': active}" class="modal">
    <div class="modal-background" v-on:click="hideModal"></div>
    <div class="modal-content">
      <a class="button is-large is-borderless is-marginless" v-on:click="react('like')">
        <h2 class="subtitle is-3 is-emoji">👍🏻</h2>
      </a>
      <a class="button is-large is-borderless is-marginless" v-on:click="react('love')">
        <h2 class="subtitle is-3 is-emoji">♥️</h2>
      </a>
      <a class="button is-large is-borderless is-marginless" v-on:click="react('haha')">
        <h2 class="subtitle is-3 is-emoji">😆</h2>
      </a>
      <a class="button is-large is-borderless is-marginless" v-on:click="react('wow')">
        <h2 class="subtitle is-3 is-emoji">😮</h2>
      </a>
      <a class="button is-large is-borderless is-marginless" v-on:click="react('sad')">
        <h2 class="subtitle is-3 is-emoji">😭</h2>
      </a>
      <a class="button is-large is-borderless is-marginless" v-on:click="react('angry')">
        <h2 class="subtitle is-3 is-emoji">😡</h2>
      </a>
    </div>
    <button class="modal-close" v-on:click="hideModal"></button>
  </div>
</template>

<script type="text/babel">
  export default {
    props: ['active'],
    data () {
      return {}
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
    font-size: 2.3em;
  }
  .modal-content {
    display: flex;
    justify-content: center;
  }
</style>
