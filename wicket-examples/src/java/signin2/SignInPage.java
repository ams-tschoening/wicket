///////////////////////////////////////////////////////////////////////////////////
//
// Created May 21, 2004
//
// Copyright 2004, Jonathan W. Locke
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package signin2;

import com.voicetribe.wicket.RequestCycle;
import com.voicetribe.wicket.markup.html.HtmlPage;
import com.voicetribe.wicket.markup.html.panel.SignInPanel;
import com.voicetribe.wicket.protocol.http.HttpSession;


/**
 * Abstract base class for a typical login page
 * 
 * @author Juergen Donnerstag
 */
public abstract class SignInPage extends HtmlPage
{
	private final SignInPanel signInPanel;

    /**
     * 
     * Constructor
     * @param parameters The page parameters
     */
    protected SignInPage(SignInPanel signInPanel)
    {
    	super();
    	this.signInPanel = signInPanel;
        add(signInPanel);
    }
   
	/**
	 * @see com.voicetribe.wicket.Page#checkAccess(com.voicetribe.wicket.RequestCycle)
	 */
	protected boolean checkAccess(RequestCycle cycle) {
        // Log the user in
        if (null == signInPanel.signIn(cycle, signInPanel.getUsername(), signInPanel.getPassword()))
        {
        	// Login successfull
            if (cycle.continueToOriginalDestination())
            {
            	// Page successfully redirected. No need to render page.
                return HtmlPage.ACCESS_DENIED;
            }
        }
            
       return HtmlPage.ACCESS_ALLOWED;
	}

	/**
	 * Log user out
	 *  
	 * @param cycle
	 */
	public static void logout(final RequestCycle cycle)
	{
		try
		{
			((HttpSession)cycle.getSession()).getHttpServletSession().invalidate();
		}
		catch (IllegalStateException e)
		{
			; // ignore
		}
	}
}

///////////////////////////////// End of File /////////////////////////////////
