<!-- TODO Overlay pop-up in navbar -->

<template>
  <div>
    <div class="container">
      <div class="columns">
        <div class="column is-half is-offset-one-quarter">

          <form v-on:submit.prevent="doPost">

            <div class="card">
              <div class="card-content">
                <h1 class="title is-1">New Post</h1>

                <div class="field">
                  <label class="label">Caption</label>
                  <p class="control">
                    <textarea class="textarea" v-model="form.body.caption" placeholder="Some Caption..."></textarea>
                  </p>
                </div>

                <div class="field">
                  <label class="label">Select file</label>
                  <p class="control">
                    <input type="file" v-on:change="selectFile">
                  </p>
                </div>
              </div>

              <div class="card-image" v-if="form.selectedFile">
                <figure class="image">
                  <img :src="form.body.file" />
                </figure>
              </div>

              <footer class="card-footer">
                <a class="card-footer-item" v-on:click="doPost">Post</a>
              </footer>
            </div>

          </form>

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
  </div>
</template>

<script type="text/babel">
  export default {
    data () {
      return {
        context: 'new post context',
        form: {
          selectedFile: false,
          body: {
            file: '',
            caption: '',
            type: 'image'
          }
        },
        error: null
      }
    },
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
        this.form.body.file = ''
        this.form.selectedFile = false

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
            this.form.body.file = e.target.result
            this.form.selectedFile = true
          }
          reader.readAsDataURL(file)
        }
      },
      doPost () {
        if (!this.form.selectedFile) {
          this.error = 'Please select file'
          this.showModal()
          return
        }

        this.$http.post('posts', this.form.body)
        .then(
          // Success
          () => {
            console.log('Post complete')
            this.$router.push({ path: '/' })
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

  a.card-footer-item {
    font-size: 1.5rem;
    font-weight: bold;

    background-color: #00D1B2;
    color: white;

    &:hover {
      background-color: #00c4a7;
      color: #fff;
    }

    &:active {
      background-color: #00b89c;
      box-shadow: inset 0 1px 2px rgba(10, 10, 10, 0.2);
      color: #fff;
    }
  }
</style>
