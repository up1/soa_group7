<template>
  <div class="search" v-if="active">
    <div class="arrow"></div>
    <div class="search-box">
      <div class="content">
        <div class="list" v-for="noti in this.notis" :key="noti.id">
          <div>
            <div class="name">
              <p class="name is-bold">{{noti.text}}</p>
              <p class="name">{{noti.time | moment("MMM D, YYY, h:mm A")}}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script type="text/babel">
  export default {
    props: ['active'],
    data () {
      return {
        notis: []
      }
    },
    created () {
      this.$http.get('notifications')
        .then((response) => (this.notis = response.body))
    }
  }
</script>

<style scoped>
  .search {
    position: absolute;
    margin-top:50px;
  }
  .arrow {
    border: solid 1px #e6e6e6;
    box-shadow: 0 0 5px 1px rgba(0,0,0,.0975);
    background-color: #fff;
    height: 14px;
    right: 90px;
    margin: auto;
    position: relative;
    -webkit-transform: rotate(45deg);
    transform: rotate(45deg);
    width: 14px;
    top: 12px;
    z-index: 1;
  }
  .search-box {
    background: #fff;
    border-radius: 3px;
    box-shadow: 0 0 5px rgba(0,0,0,.0975);
    display: block;
    position: absolute;
    right: 0;
    top: 18px;
    width: 245px;
    z-index: 9;
  }
  .search-box > .content {
    max-height: 362px;
    overflow-y: auto;
    overflow-x: hidden;
    padding: 0;
    -webkit-overflow-scrolling: touch;
  }
  .search-box > .content > .list {
    align-items: center;
    border-bottom: solid 1px #dbdbdb;
    display: flex;
    flex-direction: row;
    flex-shrink: 0;
    padding: 12px 16px;
  }
  .search-box > .content > .list > div {
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
