export const parseRich = function(content) {
	    const singalList = [
	      ['&quot;', '"'],
	      ['&amp;', '&'],
	      ['&lt;', '<'],
	      ['&gt;', '>'],
	      ['&nbsp;', ' ']
	    ];
	    const tagList = ['p', 'span', 'img', 'a', 'div', 'h1', 'h2', 'h3', 'h4', 'h5', 'font', 'b', 'i', 'u', 'code', 'table', 'tr', 'td', 'th']
	    singalList.forEach(i => {
	      content = content.replace(new RegExp(i[0], 'g'), i[1])
	    })
	    tagList.forEach(i => {
	      content = content.replace(new RegExp(`<${i} `, 'gi'), `<${i} class="rich-${i}-class" `)
	    })
	    return content;
}