/*
 * Copyright 2010-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.plugin.libraries;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.PlatformTestCase;
import org.jetbrains.jet.cli.KotlinCompiler;
import org.jetbrains.jet.plugin.PluginTestCaseBase;

/**
 * @author Evgeny Gerashchenko
 * @since 3/23/12
 */
public abstract class AbstractLibrariesTest extends PlatformTestCase {
    protected static final String PACKAGE = "testData.libraries";
    protected static final String TEST_DATA_PATH = PluginTestCaseBase.getTestDataPathBase() + "/libraries";
    protected VirtualFile libraryDir;
    protected VirtualFile librarySourceDir;

    protected abstract boolean isWithSources();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        String libraryPath = FileUtil.getTempDirectory();
        librarySourceDir = LocalFileSystem.getInstance().findFileByPath(TEST_DATA_PATH + "/library");
        assertNotNull(librarySourceDir);
        KotlinCompiler.ExitCode compilerExec = new KotlinCompiler().exec("-src", librarySourceDir.getPath(), "-output", libraryPath);
        assertEquals(KotlinCompiler.ExitCode.OK, compilerExec);
        libraryDir = LocalFileSystem.getInstance().findFileByPath(libraryPath);
        assertNotNull(libraryDir);

        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                ModifiableRootModel moduleModel = ModuleRootManager.getInstance(myModule).getModifiableModel();

                Library.ModifiableModel libraryModel = moduleModel.getModuleLibraryTable().getModifiableModel().createLibrary("myKotlinLib").getModifiableModel();
                libraryModel.addRoot(libraryDir, OrderRootType.CLASSES);
                if (isWithSources()) {
                    libraryModel.addRoot(librarySourceDir, OrderRootType.SOURCES);
                }
                libraryModel.commit();

                moduleModel.commit();
            }
        });
    }
}
