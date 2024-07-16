<template>
	<web-view :src="src"></web-view>
</template>

<script>
	import constants from '@/constants/index'
	export default {
		data() {
			return {
				src: constants.website
			};
		},
		onLoad() {}
	}
</script>

<style lang="scss">

</style>