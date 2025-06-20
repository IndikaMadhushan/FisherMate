"use client"

import { useState, useEffect } from "react"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import {
  Anchor,
  ArrowLeft,
  Calendar,
  MapPin,
  Users,
  Clock,
  Fuel,
  Droplets,
  UtensilsCrossed,
  Trash2,
} from "lucide-react"
import Link from "next/link"
import { useRouter } from "next/navigation"

interface HistoryEntry {
  id: number
  date: string
  location: string
  duration: number
  crew: number
  distance: number
  weather: any
  calculation: any
}

export default function HistoryPage() {
  const [history, setHistory] = useState<HistoryEntry[]>([])
  const router = useRouter()

  useEffect(() => {
    // Check authentication
    const isLoggedIn = localStorage.getItem("fisherman_logged_in")
    if (!isLoggedIn) {
      router.push("/login")
      return
    }

    // Load history
    const savedHistory = localStorage.getItem("trip_history")
    if (savedHistory) {
      setHistory(JSON.parse(savedHistory))
    }
  }, [router])

  const clearHistory = () => {
    if (confirm("Are you sure you want to clear all trip history?")) {
      localStorage.removeItem("trip_history")
      setHistory([])
    }
  }

  const deleteEntry = (id: number) => {
    if (confirm("Are you sure you want to delete this trip record?")) {
      const updatedHistory = history.filter((entry) => entry.id !== id)
      setHistory(updatedHistory)
      localStorage.setItem("trip_history", JSON.stringify(updatedHistory))
    }
  }

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString("en-US", {
      year: "numeric",
      month: "short",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    })
  }

  const formatLocation = (location: string) => {
    return location
      .split("-")
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
      .join(" ")
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-cyan-100">
      {/* Header */}
      <div className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-4">
            <div className="flex items-center space-x-3">
              <Link href="/dashboard">
                <Button variant="ghost" size="sm">
                  <ArrowLeft className="h-4 w-4 mr-2" />
                  Back to Dashboard
                </Button>
              </Link>
              <div className="flex items-center space-x-3">
                <Anchor className="h-8 w-8 text-blue-600" />
                <h1 className="text-2xl font-bold text-gray-900">Trip History</h1>
              </div>
            </div>
            {history.length > 0 && (
              <Button variant="outline" size="sm" onClick={clearHistory}>
                <Trash2 className="h-4 w-4 mr-2" />
                Clear All
              </Button>
            )}
          </div>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {history.length === 0 ? (
          <Card className="text-center py-12">
            <CardContent>
              <Anchor className="h-16 w-16 mx-auto text-gray-400 mb-4" />
              <h3 className="text-lg font-medium text-gray-900 mb-2">No Safe Trips Yet</h3>
              <p className="text-gray-600 mb-6">Complete safe fishing trips to see them appear in your history.</p>
              <Link href="/dashboard">
                <Button className="bg-blue-600 hover:bg-blue-700">Plan Your First Trip</Button>
              </Link>
            </CardContent>
          </Card>
        ) : (
          <div className="space-y-6">
            <div className="flex justify-between items-center">
              <h2 className="text-xl font-semibold text-gray-900">Safe Fishing Trips ({history.length})</h2>
              <Badge variant="secondary" className="bg-green-100 text-green-800">
                All trips shown were deemed safe
              </Badge>
            </div>

            <div className="grid gap-6">
              {history.map((entry) => (
                <Card key={entry.id} className="hover:shadow-lg transition-shadow">
                  <CardHeader>
                    <div className="flex justify-between items-start">
                      <div>
                        <CardTitle className="flex items-center text-lg">
                          <MapPin className="h-5 w-5 mr-2 text-blue-600" />
                          {formatLocation(entry.location)}
                        </CardTitle>
                        <CardDescription className="flex items-center mt-1">
                          <Calendar className="h-4 w-4 mr-2" />
                          {formatDate(entry.date)}
                        </CardDescription>
                      </div>
                      <div className="flex items-center space-x-2">
                        <Badge className="bg-green-100 text-green-800">Safe Trip</Badge>
                        <Button
                          variant="ghost"
                          size="sm"
                          onClick={() => deleteEntry(entry.id)}
                          className="text-red-600 hover:text-red-700"
                        >
                          <Trash2 className="h-4 w-4" />
                        </Button>
                      </div>
                    </div>
                  </CardHeader>
                  <CardContent>
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                      {/* Trip Details */}
                      <div className="space-y-3">
                        <h4 className="font-medium text-sm text-gray-700">Trip Details</h4>
                        <div className="space-y-2 text-sm">
                          <div className="flex items-center">
                            <Clock className="h-4 w-4 mr-2 text-gray-500" />
                            {entry.duration} days
                          </div>
                          <div className="flex items-center">
                            <Users className="h-4 w-4 mr-2 text-gray-500" />
                            {entry.crew} crew members
                          </div>
                          <div className="flex items-center">
                            <MapPin className="h-4 w-4 mr-2 text-gray-500" />
                            {entry.distance} nautical miles
                          </div>
                        </div>
                      </div>

                      {/* Weather Conditions */}
                      <div className="space-y-3">
                        <h4 className="font-medium text-sm text-gray-700">Weather</h4>
                        <div className="space-y-2 text-sm">
                          <div>Temp: {entry.weather.temperature}°F</div>
                          <div>Wind: {entry.weather.windSpeed} mph</div>
                          <div>Waves: {entry.weather.waveHeight} ft</div>
                          <div>Condition: {entry.weather.condition}</div>
                        </div>
                      </div>

                      {/* Resources Used */}
                      <div className="space-y-3">
                        <h4 className="font-medium text-sm text-gray-700">Resources</h4>
                        <div className="space-y-2 text-sm">
                          <div className="flex items-center">
                            <UtensilsCrossed className="h-4 w-4 mr-2 text-green-600" />
                            {entry.calculation.food} kg food
                          </div>
                          <div className="flex items-center">
                            <Droplets className="h-4 w-4 mr-2 text-blue-600" />
                            {entry.calculation.water} L water
                          </div>
                          <div className="flex items-center">
                            <Fuel className="h-4 w-4 mr-2 text-orange-600" />
                            {entry.calculation.fuel} gal fuel
                          </div>
                        </div>
                      </div>

                      {/* Safety Score */}
                      <div className="space-y-3">
                        <h4 className="font-medium text-sm text-gray-700">Safety</h4>
                        <div className="space-y-2">
                          <div className="flex items-center justify-between">
                            <span className="text-sm">Score:</span>
                            <Badge className="bg-green-100 text-green-800">{entry.calculation.safetyScore}/100</Badge>
                          </div>
                          {entry.calculation.warnings.length > 0 && (
                            <div className="text-xs text-amber-700">{entry.calculation.warnings.length} warning(s)</div>
                          )}
                        </div>
                      </div>
                    </div>
                  </CardContent>
                </Card>
              ))}
            </div>
          </div>
        )}
      </div>
    </div>
  )
}
