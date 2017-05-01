<template>
  <form v-on:submit.prevent="editProfilePicture">

    <div class="field is-horizontal" style="margin-bottom: 1.3rem;">
      <div class="field-label is-normal">
        <label class="label"></label>
      </div>
      <div class="field-body">
        <figure class="image profile-figure">
          <img v-if="selectedFile" class="profile-img" :src="file" />
        </figure>
      </div>
    </div>

    <div class="field is-horizontal form">
      <div class="field-label is-normal">
        <label class="label">Select File</label>
      </div>
      <div class="field-body">
        <div class="field">
          <p class="control">
            <input id="file" type="file" class="inputfile" v-on:change="selectFile">
            <label for="file">
              <i class="fa fa-upload" aria-hidden="true"></i>
              <span>Choose a file</span>
            </label>
          </p>
        </div>
      </div>
    </div>

    <div class="field is-horizontal">
      <div class="field-label">
        <!-- Left empty for spacing -->
      </div>
      <div class="field-body">
        <div class="field">
          <div class="control">
            <button type="submit" class="button is-primary">
              Upload picture
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="modal">
      <div class="modal-background" v-on:click="hideModal"></div>
      <div class="modal-content">
        <div class="box">
          <span class="tag is-pulled-left is-danger is-large" style="margin-right: 0.65rem;">Error</span><h1 class="title is-2 is-pulled-left" style="margin-bottom: 0;">{{ this.error }}</h1>
          <a class="button is-pulled-right is-medium is-primary" v-on:click="hideModal">OK</a>
          <span class="is-clearfix"></span>
        </div>
      </div>
      <button class="modal-close" v-on:click="hideModal"></button>
    </div>
  </form>
</template>

<script type="text/babel">
  import {mapGetters} from 'vuex'
  export default {
    created () {
      this.$store.dispatch('fetchUser', this.$auth.user().id)
    },
    data () {
      return {
        selectedFile: false,
        file: '',
        error: null
      }
    },
    computed: mapGetters({
      user: 'getUser'
    }),
    methods: {
      showModal () {
        $('.modal').addClass('is-active')
      },
      hideModal () {
        $('.modal').removeClass('is-active')
      },
      selectFile (e) {
        let accept = ['image/jpeg', 'image/gif', 'image/png']

        // Cleaar error
        this.error = null

        // Clear the old selected file
        this.file = ''
        this.selectedFile = false

        // Let's check file types
        let files = e.target.files
        if (files.length > 0) {
          let file = files[0]
          let valid = _.indexOf(accept, file.type) > -1

          // If not valid, alert
          if (!valid) {
            this.error = 'Invaild File Type'
            this.showModal()

            e.target.value = ''
            if (e.target.value) {
              e.target.type = 'text'
              e.target.type = 'file'
            }

            return
          }

          // If valid, read the file
          var reader = new FileReader()
          reader.onload = (e) => {
            this.file = e.target.result
            this.selectedFile = true
          }
          reader.readAsDataURL(file)
        }
      },
      editProfilePicture () {
        if (!this.selectedFile) {
          this.error = 'Please select file'
          this.showModal()
          return
        }

        this.$http.patch('users/self/picture', { profile_picture: this.file })
        .then((response) => {
          console.log(response.body)
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .profile-img {
    width: 300px;
    height: 300px;
  }

  .inputfile {
    width: 0.1px;
    height: 0.1px;
    opacity: 0;
    overflow: hidden;
    position: absolute;
    z-index: -1;

    & + label {
      max-width: 80%;
      font-size: 1.25rem;
      font-weight: 700;
      text-overflow: ellipsis;
      white-space: nowrap;
      cursor: pointer;
      display: inline-block;
      overflow: hidden;
      padding: 0.325rem 0.75rem;
      background-color: #00D1B2;
      color: #fff;

      i {
        width: 1em;
        height: 1em;
        vertical-align: middle;
        fill: currentColor;
        margin-top: -0.25em;
        margin-right: 0.25em;
      }
    }

    &:focus + label, & + label:hover {
      background-color: #00A38B;
    }
  }
</style>
