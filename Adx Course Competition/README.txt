As we are using Second price auction, bidding the true value is best case scenario.
But it is a very weakly dominant strategy. As every one can bid 1 and totally it can become random.
So other weakly dominat equilibrium could be one or more bids slightly more than 1 can be bid taking everyone will mostly bid 1. 

But this can incur loss as everyone starts bidding more than one, so I have used a pacing multiplier and a increasing and decreasing coefficient so that whenever there is a win in the bid we reduce the bid amount from 1, and if it losses the bid, then we tend to increase the bid value to a higher value by multiplying with a constant increase factor.

 This we the bid will reduce if there is no one to bid higher and so we can make a good profit. And at the same time not going so much high so that we always end up paying more than the budget.
	private Double DECREASING_FACTOR; // for decreasing a gradual reduction.
	private Double INCREASING_FACTOR; // for decreasing a gradual increase.
	private static Double INITVALUE = 1.1; // initial value.

and each market segment will have a unique multiplier bid value.

DECREASING_FACTOR is set to 1.01, INCREASING_FACTOR = 1.1
where these are the best performing values compared to best case always 1.


And to run the code you need to add the util class also, where the distribution probablities are coded.
adx.utils.MarketSegmentProbabilities