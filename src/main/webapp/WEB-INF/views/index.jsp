<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Mortgage PRO</title>
	<link rel="stylesheet" type="text/css" href="http://s.xnimg.cn/external/gta/css/reset.css?ver=$revxxx$">
	<link rel="stylesheet" type="text/css" href="http://s.xnimg.cn/external/gta/css/home.css?ver=$revxxx$">
	<script type="text/javascript" src="http://s.xnimg.cn/ajax/jquery/jquery-1.11.0.min.js"></script>
</head>
<body id="home">
	<!--search start-->
	<div class="search-wrap wrap-full">
		<img src="http://s.xnimg.cn/external/gta/images/home_bg.jpg" class="big-bg">
		<div class="bigger">Bigger than bigger</div>
		<div id="search" class="clearfix">
			<input type="text" placeholder="I think you need help!">
			<a href="javascript:void(0)" class="search-btn"><i class="search-icon"></i></a>
		</div>
	</div>
	<!--search end-->

	<!--Refinance start-->
	<div class="refinance content">
	    <div class="detail-box">
	        <div class="detail-title">Refinance Rates</div>
	        <div class="detail-text">Mortgage PRO helps you refinance by providing you personalized loan rates without requiring your contact information. Begin by entering a few details in the form to get rate quotes from our network of participating lenders who have ratings and reviews from Mortgage PRO customers. Then, select the best offer for you based on loan type, APR, lender reviews, and more so you can lower your monthly mortgage payment.</div>
	        <a href="#" class="button">Enter details right now!</a>
	    </div>
	    <div class="detail-img">
	    	<img src="http://s.xnimg.cn/external/gta/images/refinance_bg.jpg">
	    </div>
	</div>
	<!--Refinance end-->

	<!--Mortgage start-->
	<div class="mortgage-wrap wrap-full">
	    <div class="mortgage content">
	    <div class="detail-box">
	        <div class="detail-title">Mortgage Rates</div>
	        <div class="detail-text">Mortgage PRO makes it easier to buy a home by allowing you to shop for customized mortgage quotes without providing any personal information. Fill out the short form at the top of the page to get today's loan quotes quickly and anonymously. Then, compare offers by mortgage interest rate, APR, fees, monthly payment, and more without the hassle of unwanted phone calls or emails.</div>
	        <a href="#" class="button">Get today’s loan quotes!</a>
	    </div>
		    <div class="detail-img">
		    	<img src="http://s.xnimg.cn/external/gta/images/mortgage_bg.jpg">
		    </div>
	    </div>
	</div>
	<!--Mortgage end-->

	<!--advice start-->
	<div class="calculator middle">
	    <div class="text-box">
			<div class="title">Calculators</div>
			<div class="text">Mortgage PRO's calculators helps determine your total monthly mortgage payment, inclusive of principal, interest, taxes, and insurance. The payment amount is based on the value of the home, with the calculator setting default values for property taxes, home insurance, mortgage insurance (PMI), and other details. You are able to adjust these details to reflect your situation and get a more accurate monthly payment estimate. </div>
	    </div>
		<div class="type clearfix">
			<a href="#">
				<p class="big-txt">Rent vs Buy</p>
				<p class="small-txt">Mortgage PRO helps you find the perfect home</p>
			</a>
			<a href="#">
				<p class="big-txt">Affordability</p>
				<p class="small-txt">See how much house you can afford</p>
			</a>
			<a href="#">
				<p class="big-txt">Mortgage</p>
				<p class="small-txt">Estimate your monthly mortgage payment</p>
			</a>
			<a href="#">
				<p class="big-txt">Refinance</p>
				<p class="small-txt">See how much refinancing can save you</p>
			</a>
		</div>
	</div>
	<!--advice end-->

	<!--Calculators start-->
	<div class="advice-wrap wrap-full">
		<div class="advice middle">
		    <div class="text-box">
				<div class="title">News & Advice</div>
				<div class="text">You can find any essential and valuable imformation in this part, including useful Q&A and most timely articles. </div>
		    </div>
		    <img class="book" src="http://s.xnimg.cn/external/gta/images/book.jpg">
			<a href="javascript:void(0);" class="current button">Learn more imformation!</a>
		</div>
	</div>
	<!--Calculators end-->

</body>
<script type="text/javascript">
	$(document).ready(function(){
	    $(".calculator a").hover(function(){
	        var self = $(this);
	        self.find(".big-txt").addClass("white");
	        self.find(".small-txt").addClass("grey");
	    },function(){
	        var self = $(this);
	        self.find(".big-txt").removeClass("white");
	        self.find(".small-txt").removeClass("grey");
	    });
	});
</script>
</html>