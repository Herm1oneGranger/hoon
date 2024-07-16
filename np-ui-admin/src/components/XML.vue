<template>
  <a-drawer width="600px" title="XML" placement="right" :visible="visible" @close="onClose">
    <pre v-if="xmlString">
    <code >{{ xmlString }}</code>
 </pre>
  </a-drawer>
</template>

<script>
  import vkbeautify from 'vkbeautify'

export default {
  data () {
    return {
      xmlString: '',
      visible: false
    }
  },
  methods: {
    onClose () {
      this.visible = false
    },
    onOpen (xmlString) {
      this.xmlString = vkbeautify.xml(xmlString)
      this.visible = true
    },
  }
}
</script>