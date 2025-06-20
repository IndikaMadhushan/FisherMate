"use client"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Anchor, Cloud, Shield, History, Users } from "lucide-react"
import Link from "next/link"

export default function HomePage() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-cyan-100">
      {/* Hero Section */}
      <div className="relative overflow-hidden">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24">
          <div className="text-center">
            <div className="flex justify-center mb-8">
              <div className="p-4 bg-blue-100 rounded-full">
                <Anchor className="h-16 w-16 text-blue-600" />
              </div>
            </div>
            <h1 className="text-4xl md:text-6xl font-bold text-gray-900 mb-6">Fisherman's Weather App</h1>
            <p className="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
              Plan safe fishing trips with real-time weather data, resource calculations, and safety assessments. Your
              trusted companion for every fishing adventure.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <Link href="/signup">
                <Button size="lg" className="bg-blue-600 hover:bg-blue-700 px-8 py-3">
                  Get Started
                </Button>
              </Link>
              <Link href="/login">
                <Button size="lg" variant="outline" className="px-8 py-3">
                  Sign In
                </Button>
              </Link>
            </div>
          </div>
        </div>
      </div>

      {/* Features Section */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div className="text-center mb-16">
          <h2 className="text-3xl font-bold text-gray-900 mb-4">Everything You Need for Safe Fishing</h2>
          <p className="text-lg text-gray-600 max-w-2xl mx-auto">
            Our comprehensive platform helps fishermen plan safer trips with accurate weather data and resource
            planning.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          <Card className="text-center hover:shadow-lg transition-shadow">
            <CardHeader>
              <div className="flex justify-center mb-4">
                <div className="p-3 bg-blue-100 rounded-full">
                  <Cloud className="h-8 w-8 text-blue-600" />
                </div>
              </div>
              <CardTitle className="text-lg">Weather Monitoring</CardTitle>
            </CardHeader>
            <CardContent>
              <CardDescription>
                Real-time weather conditions including wind speed, wave height, visibility, and storm tracking.
              </CardDescription>
            </CardContent>
          </Card>

          <Card className="text-center hover:shadow-lg transition-shadow">
            <CardHeader>
              <div className="flex justify-center mb-4">
                <div className="p-3 bg-green-100 rounded-full">
                  <Shield className="h-8 w-8 text-green-600" />
                </div>
              </div>
              <CardTitle className="text-lg">Safety Assessment</CardTitle>
            </CardHeader>
            <CardContent>
              <CardDescription>
                Intelligent safety scoring system that evaluates trip conditions and provides go/no-go recommendations.
              </CardDescription>
            </CardContent>
          </Card>

          <Card className="text-center hover:shadow-lg transition-shadow">
            <CardHeader>
              <div className="flex justify-center mb-4">
                <div className="p-3 bg-orange-100 rounded-full">
                  <Users className="h-8 w-8 text-orange-600" />
                </div>
              </div>
              <CardTitle className="text-lg">Resource Planning</CardTitle>
            </CardHeader>
            <CardContent>
              <CardDescription>
                Calculate exact food, water, and fuel requirements based on crew size, trip duration, and distance.
              </CardDescription>
            </CardContent>
          </Card>

          <Card className="text-center hover:shadow-lg transition-shadow">
            <CardHeader>
              <div className="flex justify-center mb-4">
                <div className="p-3 bg-purple-100 rounded-full">
                  <History className="h-8 w-8 text-purple-600" />
                </div>
              </div>
              <CardTitle className="text-lg">Trip History</CardTitle>
            </CardHeader>
            <CardContent>
              <CardDescription>
                Keep detailed records of all your safe fishing trips with weather conditions and resource usage.
              </CardDescription>
            </CardContent>
          </Card>
        </div>
      </div>

      {/* CTA Section */}
      <div className="bg-blue-600 text-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
          <div className="text-center">
            <h2 className="text-3xl font-bold mb-4">Ready to Fish Safer?</h2>
            <p className="text-xl mb-8 text-blue-100">
              Join thousands of fishermen who trust our weather app for their safety.
            </p>
            <Link href="/signup">
              <Button size="lg" variant="secondary" className="px-8 py-3">
                Start Your Free Account
              </Button>
            </Link>
          </div>
        </div>
      </div>

      {/* Footer */}
      <footer className="bg-gray-900 text-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          <div className="flex flex-col md:flex-row justify-between items-center">
            <div className="flex items-center space-x-3 mb-4 md:mb-0">
              <Anchor className="h-6 w-6" />
              <span className="text-lg font-semibold">Fisherman's Weather App</span>
            </div>
            <div className="text-sm text-gray-400">
              © 2024 Fisherman's Weather App. Built for safer fishing adventures.
            </div>
          </div>
        </div>
      </footer>
    </div>
  )
}
