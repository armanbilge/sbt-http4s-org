/*
 * Copyright 2020 http4s.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.http4s.sbt

import sbt._
import sbt.Keys._

import sbtghactions._
import sbtghactions.GenerativeKeys._
import org.typelevel.sbt._, TypelevelSonatypeCiReleasePlugin.autoImport._

object Http4sOrgPlugin extends AutoPlugin {
  object autoImport

  override def trigger = allRequirements

  override def requires = TypelevelPlugin

  override def buildSettings = organizationSettings ++ githubActionsSettings

  val organizationSettings: Seq[Setting[_]] =
    Seq(
      organization := "org.http4s",
      organizationName := "http4s.org"
    )

  val githubActionsSettings: Seq[Setting[_]] =
    Seq(
      tlCiReleaseBranches := Seq("main"),
      githubWorkflowJavaVersions := List("8", "11", "17").map(JavaSpec.temurin(_)),
      githubWorkflowBuild +=
        WorkflowStep.Sbt(
          List("doc"),
          name = Some("Build docs"),
          cond = Some("matrix.ci == '' || matrix.ci == 'ciJVM'")),
      githubWorkflowBuildMatrixFailFast := Some(false),
      githubWorkflowTargetBranches := Seq("**")
    )

}
