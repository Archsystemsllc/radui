
<!DOCTYPE html5>
<html>
<head>
<link rel="icon" type="image/ico" href="/img/favicon.ico" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="title" content="jQuery Tree Multiselect - Patosai " />
<meta name="description" content="The personal website of Patrick Tsai" />
<title>jQuery Tree Multiselect - Patosai</title>
</head>
<body>
	<header>
		<input id="show-nav" type="checkbox" /><label id="hamburger"
			for="show-nav"><svg viewBox="0 0 32 32" width="32px"
				height="32px">
				<path
					d="M4,10h24c1.104,0,2-0.896,2-2s-0.896-2-2-2H4C2.896,6,2,6.896,2,8S2.896,10,4,10zM28,14H4c-1.104,0-2,0.896-2,2s0.896,2,2,2h24c1.104,0,2-0.896,2-2S29.104,14,28,14z M28,22H4c-1.104,0-2,0.896-2,2s0.896,2,2,2h24c1.104,0,2-0.896,2-2S29.104,22,28,22z"></path></svg></label>
		<nav>
			<ul>
				<li><a href="/">home</a></li>
				<li><a href="/about">about/contact</a></li>
				<li><a href="/projects">projects</a></li>
			</ul>
		</nav>
	</header>
	<div class="wrapper">
		<h1 class="center">
			<a href="https://github.com/patosai/tree-multiselect.js">jQuery
				Tree Multiselect</a>
		</h1>
		<p>This is a simple replacement for select elements if you have
			nested/sectioned options. Rather than ramble on some more, I'll just
			show you the effect. You happen to be on a web page anyway.</p>
		<code class="language-html">// HTML &lt;select
			id=&quot;demo1&quot; multiple=&quot;multiple&quot;&gt; &lt;option
			value=&quot;one&quot; data-section=&quot;top&quot;
			selected=&quot;selected&quot;
			data-index=&quot;3&quot;&gt;One&lt;/option&gt; &lt;option
			value=&quot;two&quot; data-section=&quot;top&quot;
			selected=&quot;selected&quot;
			data-index=&quot;1&quot;&gt;Two&lt;/option&gt; &lt;option
			value=&quot;three&quot; data-section=&quot;top&quot;
			selected=&quot;selected&quot; data-index=&quot;2&quot;
			data-description=&quot;A natural number&quot;&gt;Three&lt;/option&gt;
			&lt;option value=&quot;four&quot;
			data-section=&quot;top&quot;&gt;Four&lt;/option&gt; &lt;option
			value=&quot;wow&quot;
			data-section=&quot;top/inner/this/is/really/nested&quot;&gt;Wow&lt;/option&gt;
			&lt;/select&gt; </code>
		<code class="language-javascript">
		// JavaScript var params = {
			sortable: true }; $("select").treeMultiselect(params);</code>
		<select id="demo1" multiple="multiple"><option value="one"
				data-section="top" selected="selected" data-index='3'>One</option>
			<option value="two" data-section="top" selected="selected"
				data-index='1'>Two</option>
			<option value="three" data-section="top" selected="selected"
				data-index='2' data-description="A natural number">Three</option>
			<option value="four" data-section="top">Four</option>
			<option value="wow" data-section="top/inner/this/is/really/nested">Wow</option></select>
		<hr style="margin-top: 2em" />
		<p>So how does this all work?</p>
		<p>It takes the original select that you put in and hides it. Then
			it creates a double with the sweet UI that you see, and updates the
			select as you add/remove options. You can see that in the second
			demo, along with the search capabilities.</p>
		<code class="language-javascript">
		$("select").treeMultiselect({searchable:
			true, searchParams: ['section', 'text']});
		</code>
		<select id="demo2" style="margin: 0 auto; margin-bottom: 1em">
			<option value="blueberry" data-section="Smoothies">Blueberry</option>
			<option value="strawberry" data-section="Smoothies">Strawberries</option>
			<option value="peach" data-section="Smoothies">Peach</option>
			<option value="milk tea" data-section="Smoothies/Bubble Tea" selected="selected">Milk Tea</option>
			<option value="green apple" data-section="Smoothies/Bubble Tea">Green Apple</option>
			<option value="passion fruit" data-section="Smoothies/Bubble Tea"
				selected="selected" readonly="readonly">Passion Fruit</option></select>
	</div>
	<footer></footer>
	<link rel="stylesheet" href="/css/style.css" />
	<script type="text/javascript" src="/vendor/js/prism.js"></script>
	<link rel="stylesheet" href="/vendor/css/prism.css" />
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"
		integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
		crossorigin="anonymous"></script>
	<script type="text/javascript"
		src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.rawgit.com/patosai/tree-multiselect/v2.4.1/dist/jquery.tree-multiselect.min.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("select#demo1").treeMultiselect({
				sortable : true
			});
			$("select#demo2").treeMultiselect({
				searchable : true,
				searchParams : [ 'section', 'text' ]
			});
			$("select#demo2").css('display', 'block');
		});
	</script>
	<link rel="stylesheet" href="https://cdn.rawgit.com/patos ai/tree-multiselect/v2.4.1/dist/jquery.tree-multiselect.min.css" />
</body>
</html>