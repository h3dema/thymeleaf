/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * <p>
 *   Interface defining operations that can regulate the pace at which the template engine will process a
 *   template (a.k.a. <em>engine throttling</em>). Objects implementing this interface are returned by
 *   the <tt>processThrottled(...)</tt> methods at {@link ITemplateEngine}.
 * </p>
 * <p>
 *   When the processing of a template is <em>throttled</em> the client classes can tell the engine how much output
 *   they are prepared to handle by calling any of the <tt>process(int,...)</tt> methods. As a response to this,
 *   the engine will process only the part of the template enough to write <strong>at most</strong> so many chars
 *   or bytes as specified at the <tt>processThrottled(...)</tt> call. Output will be written to a {@link Writer}
 *   in the form of chars, or to an {@link OutputStream} in the form of bytes.
 * </p>
 * <p>
 *   Once the desired amount of output has been written, the engine stops where it is with minimum
 *   or no pending output caching, and returns control to the caller, so that the client can
 *   process output and prepare for a subsequent call. Note that this whole process is
 *   <strong>single-threaded</strong>.
 * </p>
 * <p>
 *   Among other scenarios, this should allow Thymeleaf to be efficiently integrated as a
 *   <strong>back-pressure</strong>-driven <em>cold observable</em> in a <strong>reactive architecture</strong>.
 * </p>
 *
 * @author Daniel Fern&aacute;ndez
 *
 * @since 3.0.0
 *
 */
public interface IThrottledTemplateProcessor {

    /**
     * <p>
     *   Checks whether the processing of the template has already finished.
     * </p>
     *
     * @return true if the template has already been fully processed, false if not.
     */
    public boolean isFinished();

    /**
     * <p>
     *   Process the whole template (all parts remaining), with no limit in the amount of chars written to output.
     * </p>
     * @param writer the writer output should be written to.
     */
    public void processAll(final Writer writer);

    /**
     * <p>
     *   Process the whole template (all parts remaining), with no limit in the amount of bytes written to output.
     * </p>
     * @param outputStream the output stream output should be written to.
     * @param charset the charset to be used for encoding the written output into bytes.
     */
    public void processAll(final OutputStream outputStream, final Charset charset);

    /**
     * <p>
     *   Process the template until at most the specified amount of chars has been written to output, then return control.
     * </p>
     *
     * @param maxOutputInChars the maximum amount of chars that the engine is allowed to output. A number &lt; 0 or
     *                           {@link Integer#MAX_VALUE} will mean "no limit".
     * @param writer the writer output should be written to.
     */
    public void process(final int maxOutputInChars, final Writer writer);

    /**
     * <p>
     *   Process the template until at most the specified amount of bytes has been written to output, then return control.
     * </p>
     *
     * @param maxOutputInBytes the maximum amount of bytes that the engine is allowed to output. A number &lt; 0 or
     *                           {@link Integer#MAX_VALUE} will mean "no limit".
     * @param outputStream the output stream output should be written to.
     * @param charset the charset to be used for encoding the written output into bytes.
     */
    public void process(final int maxOutputInBytes, final OutputStream outputStream, final Charset charset);


}
