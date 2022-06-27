package com.Aurages.securitymodule;

interface SecurityDelegate 
{
	void CheckEndedWithResult(int resultCode);
	
	void RequestReturnedResponse(int requestStatus);
}
